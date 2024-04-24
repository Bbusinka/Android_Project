package com.example.todo_app.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class HomeFragment extends Fragment {

    public ListView taskList; //змінна елементу для списку
    ArrayList<TaskAdapter> taskadap; //змінна класу адаптеру списку

    ArrayList<String> itemkey = new ArrayList<String>(); //змінна списку з id полями

    private FirebaseUser user;  //змінна класу користувача
    private FirebaseAuth mAuth; //змінна класу входу до профіля
    private DatabaseReference mRef; //змінна класу з нашою базою данних

    ProgressBar progressBar; //змінна елементу з прогресом виконання
    TextView proc,donet,allt,leftt; //змінні єлементів тесту

    View view; //змінна інтерфейсу користувача

    int donecount, procdone, posit,alltask,alldonecount; //числові змінні для розрахунків

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        taskList = (ListView) view.findViewById(R.id.tasklistView);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar2);
        proc = (TextView) view.findViewById(R.id.textView);
        donet = (TextView) view.findViewById(R.id.text_done_tasks);
        allt = (TextView) view.findViewById(R.id.text_all_tasks);
        leftt = (TextView) view.findViewById(R.id.text_left_tasks);

        mAuth = FirebaseAuth.getInstance();
        user =mAuth.getCurrentUser();

        taskadap = new ArrayList<TaskAdapter>();
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
    public void getDataDB(){
        mRef = FirebaseDatabase.getInstance().getReference("Task");

        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        String dateText = dateFormat.format(currentDate);

            ValueEventListener vListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    //очіщює список переж записомнових данних
                    if (taskadap.size()>0) {
                        taskadap.clear();
                        donecount = 0;
                        posit = 0;
                        alltask = 0;
                        alldonecount = 0;
                        procdone = 0;
                    }
                    for (DataSnapshot ds : snapshot.getChildren()){
                        if(user.getEmail().equals(ds.child("whom").getValue(String.class))){
                            //рахує усю кількість завдань
                            alltask++;
                            if (ds.child("taskdone").getValue(Boolean.class).equals(true)){
                                //рахує усю кількість виконаних завдань
                                alldonecount++;
                            }

                            if(ds.child("taskdatedo").getValue(String.class).equals(dateText)){
                                if (ds.child("taskdone").getValue(Boolean.class).equals(true)){
                                    //рахує кількість виконаних завдань на сьогодні
                                    donecount++;
                                }
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
                    if (donecount>0) procdone = donecount*100/taskadap.size(); else procdone = 0;
                    donet.setText(getResources().getString(R.string.homedone)+" "+ alldonecount);
                    allt.setText(getResources().getString(R.string.homeall)+" "+ alltask);
                    leftt.setText(getResources().getString(R.string.homeleft)+" "+ (alltask-alldonecount));
                    progressBar.setProgress(procdone);
                    proc.setText(procdone+ "%");
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {}
            };
            mRef.addValueEventListener(vListener);
    }


}