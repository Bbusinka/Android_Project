package com.example.todo_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ListItem extends AppCompatActivity {

    private DatabaseReference mRef;//змінна класу з нашою базою данних
    TextView text_heading,task_author,text_desc,text_dateot,text_datedo;//змінні єлементів для виводу інформації
    Button butt_ok, butt_doc;//змінні елементів для кнопок
    CheckBox check_done;//змінна елементу для чекбоксу

    private FirebaseUser user;//змінна класу користувача
    private FirebaseAuth mAuth;//змінна класу входу до профіля

    String task_key, subject, text_email;;//текстові змінні для формування документації

    Boolean doneset;//логічна змінна для перевірки стану виконання завдання

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);

        ActionBar actionBar =getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        user =mAuth.getCurrentUser();
        //присвоєння змінним їх елементів тексту
        text_heading = (TextView) findViewById(R.id.text_heading);
        task_author = (TextView) findViewById(R.id.task_author);
        text_desc = (TextView) findViewById(R.id.text_desc);
        text_dateot = (TextView) findViewById(R.id.text_dateot);
        text_datedo = (TextView) findViewById(R.id.text_datedo);
        //присвоєння змінним їх елементів кнопок
        butt_ok = (Button) findViewById(R.id.butt_ok);
        butt_doc = (Button) findViewById(R.id.butt_screen);
        check_done = (CheckBox) findViewById(R.id.check_done);
        //заповнення єлементів інформацією
        text_heading.setText("Heading");
        task_author.setText("Author");
        text_desc.setText("Descriptoin");
        text_dateot.setText("Date create");
        text_datedo.setText("Date");
        check_done.setChecked(false);

        //бере Id запису та зчитує його інфоримацію
        Intent i = getIntent();
        if (i!=null){
           task_key = i.getStringExtra("taskkey");
           getDataDB();
        }

        butt_doc.setOnClickListener(this::SendMail);
        butt_ok.setOnClickListener(this::UpdateDone);

    }
    //Метод оновлення стану завдання
    private void UpdateDone(View view){
        Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == 1){
                    finish();
                }
            }
        };

        if (!check_done.isChecked() == doneset){
            mRef.child(task_key).child("taskdone").setValue(check_done.isChecked()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Snackbar.make(view,getResources().getString(R.string.notif_update_succ), BaseTransientBottomBar.LENGTH_LONG).show();
                    handler.sendEmptyMessageDelayed(1, 2000);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Snackbar.make(view,getResources().getString(R.string.notif_update_fail), BaseTransientBottomBar.LENGTH_LONG).show();
                    handler.sendEmptyMessageDelayed(1, 2000);
                }
            });
        } else finish();
    }
    //Метод зчитування інфірмації про запис з бази данних
    private void getDataDB(){
        mRef = FirebaseDatabase.getInstance().getReference("Task");

        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    if(task_key.equals(ds.getKey())){
                            TaskAdapter task = ds.getValue(TaskAdapter.class);
                            text_heading.setText(task.getHeading());
                            text_desc.setText(task.getDescription());
                            task_author.setText(getResources().getString(R.string.author)+task.getAuthor());
                            text_datedo.setText(getResources().getString(R.string.datedo)+task.getTaskdatedo());
                            text_dateot.setText(getResources().getString(R.string.dateot)+task.getTaskdateot());
                            check_done.setChecked(task.getTaskdone());
                            doneset = task.getTaskdone();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        mRef.addValueEventListener(vListener);
    }


    //Метод відправки повідомлення
    private void SendMail(View view){
        String standone;
        if (check_done.isChecked()){
            standone = "Стан виконання: Виконано";
        } else {
            standone = "Стан виконання: В процесі";
        }
        text_email = String.format("Заголовок заявки: "+text_heading.getText().toString() + "\n"+
                task_author.getText().toString() + "\n"+
                text_dateot.getText().toString() + "\n"+
                text_datedo.getText().toString() + "\n"+
                "Опис заявки: "+text_desc.getText().toString() + "\n"+
                standone);

        //Створення об'єкту для відправки на пошту
        final Intent emailIntent = new Intent(Intent.ACTION_SEND);
        //Налаштування типу даних що відправляються
        emailIntent.setType("plain/text");
        //Встановлення адреси отримувача
        emailIntent.putExtra(Intent.EXTRA_EMAIL,
                new String[]{user.getEmail()});
        //Встановлення теми листа
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        //Заповнення листа даними
        emailIntent.putExtra(Intent.EXTRA_TEXT, text_email);
        //Відправка листа
        startActivity(emailIntent);
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