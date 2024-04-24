package com.example.todo_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ProfileActivity extends AppCompatActivity {

    public ListView taskList;//змінна елементу для списку
    ArrayList<TaskAdapter> taskadap; //змінна класу адаптеру списку

    ArrayList<String> itemkey = new ArrayList<String>();//змінна списку з id полями

    private FirebaseUser user; //змінна класу користувача
    private FirebaseAuth mAuth; //змінна класу входу до профіля
    private DatabaseReference mRef; //змінна класу з нашою базою данних

    Button change_butt;//змінна елементу кнопки для зміни паролю
    EditText old_pass,new_pass;//змінні елементів полів для вводу сторого та нового паролю

    int posit;//числова змінна позиції Id заявки у списку

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        taskList = (ListView) findViewById(R.id.tasklistdel);
        change_butt = (Button) findViewById(R.id.change_butt);
        old_pass = (EditText) findViewById(R.id.password1);
        new_pass = (EditText) findViewById(R.id.password2);

        change_butt.setOnClickListener(this::ChangePass);

        mAuth = FirebaseAuth.getInstance();
        user =mAuth.getCurrentUser();

        taskadap = new ArrayList<TaskAdapter>();
        //виклакає діалог "видалення запису"
        taskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                openDeleteDialog(position);
            }
        });
    }

    public void onStart(){
        super.onStart();
        getDataDB();
    }

    @Override
    public void onStop() {
        super.onStop();
        getDataDB();
    }
    //Метод який слугує для зміни пароля користувача
    public void ChangePass(View view){

        if (TextUtils.isEmpty(old_pass.getText())){
            old_pass.setError(getResources().getString(R.string.error_pass));
            return;
        }else if (TextUtils.isEmpty(new_pass.getText())){
            new_pass.setError(getResources().getString(R.string.error_pass_conf));
            return;
        }

        AuthCredential credential = EmailAuthProvider
                .getCredential(user.getEmail(), String.valueOf(old_pass.getText()));
        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                    user.updatePassword(String.valueOf(new_pass.getText())).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Snackbar.make(view,getResources().getString(R.string.notif_chenge_pass), BaseTransientBottomBar.LENGTH_SHORT).show();
                        }
                    });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
    //Метод зчитування інфірмації про записи з бази данних
    public void getDataDB(){
        mRef = FirebaseDatabase.getInstance().getReference("Task");

        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //очіщює список переж записомнових данних
                if (taskadap.size()>0) {taskadap.clear(); posit = 0;}
                for (DataSnapshot ds : snapshot.getChildren()){
                    if(user.getEmail().equals(ds.child("author").getValue(String.class))){
                            //записує id запису бд
                            itemkey.add(posit,ds.getKey());
                            posit++;
                            //заповнення спаску завданнями з бд
                            TaskAdapter task = ds.getValue(TaskAdapter.class);
                            taskadap.add(task);
                            taskList.setAdapter(new ToDoArrayAdapter(ProfileActivity.this, R.layout.listview_groupview_childview, taskadap));
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
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
    //Метод діалогу "видалення запису"
    private void openDeleteDialog(int position) {
        AlertDialog.Builder quitDialog = new AlertDialog.Builder(
                this);
        quitDialog.setTitle(getResources().getString(R.string.dia_dell_task));

        quitDialog.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mRef.child(itemkey.get(position)).setValue(null);
            }
        });

        quitDialog.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        quitDialog.show();
    }
}