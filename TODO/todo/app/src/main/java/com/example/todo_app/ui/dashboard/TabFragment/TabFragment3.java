package com.example.todo_app.ui.dashboard.TabFragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.todo_app.ListItem;
import com.example.todo_app.R;
import com.example.todo_app.TaskAdapter;
import com.example.todo_app.ToDoArrayAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TabFragment3 extends Fragment {

    ArrayList<TaskAdapter> taskadap;
    public ListView taskList;

    ArrayList<String> itemkey = new ArrayList<String>();

    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;

    int posit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab3, container, false);
        taskList = (ListView) view.findViewById(R.id.tasklistview4);

        taskadap = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        user =mAuth.getCurrentUser();
        //виклакає вікно єлементу списку та передає йому Id запису
        taskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ListItem.class);
                intent.putExtra("taskkey",itemkey.get(position));
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onStart(){
        super.onStart();
        getDataDB();
    }

    @Override
    public void onStop() {
        super.onStop();
        getDataDB();
    }
    //Метод зчитування інфірмації про записи з бази данних
    private void getDataDB(){

        mRef = FirebaseDatabase.getInstance().getReference("Task");

        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //очіщює список переж записомнових данних
                if (taskadap.size()>0){ taskadap.clear(); posit = 0;}
                for (DataSnapshot ds : snapshot.getChildren()){
                    if(user.getEmail().equals(ds.child("whom").getValue(String.class))){
                        if(ds.child("taskdone").getValue(Boolean.class) == Boolean.valueOf("false")){
                            //записує id запису бд
                            itemkey.add(posit,ds.getKey());
                            posit++;
                            //заповнення спаску завданнями з бд
                            TaskAdapter task = ds.getValue(TaskAdapter.class);
                            taskadap.add(task);
                            taskList.setAdapter(new ToDoArrayAdapter(getContext(), R.layout.listview_groupview_childview, taskadap));
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        };
        mRef.addValueEventListener(vListener);
    }
}