package com.example.todo_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DataFindActivity extends AppCompatActivity {

    ArrayList<TaskAdapter> taskadap;//змінна класу адаптеру списку
    public ListView taskList;//змінна елементу для списку

    ArrayList<String> itemkey = new ArrayList<String>();//змінна списку з id полями

    private FirebaseUser user;//змінна класу користувача
    private FirebaseAuth mAuth; //змінна класу входу до профіля
    private DatabaseReference mRef; //змінна класу з нашою базою данних

    int posit;//числова змінна позиції Id заявки у списку

    String find_date;//текстова змінна для пошуку заявок по даті


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_find);

        ActionBar actionBar =getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        taskList = (ListView) findViewById(R.id.findtasklistview);

        mAuth = FirebaseAuth.getInstance();
        user =mAuth.getCurrentUser();

        taskadap = new ArrayList<>();

        Intent i = getIntent();
        if (i!=null){
            find_date = i.getStringExtra("finddate");
        }

        //виклакає вікно єлементу списку та передає йому Id запису
        taskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), ListItem.class);
                intent.putExtra("taskkey",itemkey.get(position));
                startActivity(intent);
            }
        });

    }
    @Override
    public void onStart() {
        super.onStart();
        getDataDB();
        if (taskadap.size()<1) {
            Toast.makeText(this,"Нічого не знайдено!!!",Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        getDataDB();
    }
    //Метод зчитування інфірмації про записи з бази данних
    private void getDataDB(){

        mRef = FirebaseDatabase.getInstance().getReference("Task");
        if (taskadap.size()>0){ taskadap.clear(); posit = 0;}
        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (taskadap.size()>0) taskadap.clear();
                for (DataSnapshot ds : snapshot.getChildren()){
                    if(user.getEmail().equals(ds.child("whom").getValue(String.class))){
                        if (find_date.equals(ds.child("taskdatedo").getValue(String.class))){
                            itemkey.add(posit,ds.getKey());
                            posit++;
                            TaskAdapter task = ds.getValue(TaskAdapter.class);
                            taskadap.add(task);
                            taskList.setAdapter(new ToDoArrayAdapter(DataFindActivity.this, R.layout.listview_groupview_childview, taskadap));
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        mRef.addValueEventListener(vListener);
    }
    //Метод натиску на кнопку "Додому"
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}