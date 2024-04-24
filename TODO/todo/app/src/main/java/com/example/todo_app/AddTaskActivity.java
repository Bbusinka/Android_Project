package com.example.todo_app;

import android.app.DatePickerDialog;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddTaskActivity extends AppCompatActivity {

    public Button add_but;//змінна елементу для кнопки
    public EditText add_heading, add_description, add_task_whom, add_date;//змінні полів для вводу даних

    private DatabaseReference mRef;//змінна класу для роботи з базою даних

    private String TASK = "Task";//назва таблиці в базі данних

    private String heading,description,task_whom,date;//текстові змінні для передачі даних до бд

    private int mYear, mMonth, mDay;//числові змінні для дати

    private FirebaseUser user; //змінна класу користувача
    private FirebaseAuth mAuth; //змінна класу входу до профіля

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_task);


        add_but = (Button) findViewById(R.id.add_task_butt);
        add_heading = (EditText) findViewById(R.id.add_heading);
        add_description = (EditText) findViewById(R.id.add_description);
        add_task_whom = (EditText) findViewById(R.id.add_task_whom);
        add_date = (EditText) findViewById(R.id.add_date);

        mAuth = FirebaseAuth.getInstance();
        user =mAuth.getCurrentUser();

        add_but.setOnClickListener(this::ifempty);
        add_date.setOnClickListener(this::DateDia);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
    //Метод вибору дати
    private void DateDia(View view){
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        if ((dayOfMonth<10)&&((monthOfYear+1)<10))
                            date = ("0" + dayOfMonth + "." + "0" + (monthOfYear+1) + "." + year);
                        else if((monthOfYear+1)<10)
                            date = (dayOfMonth + "." + "0" + (monthOfYear+1) + "." + year);
                        else if (dayOfMonth<10)
                            date = ("0" + dayOfMonth + "." + (monthOfYear+1) + "." + year);
                        else
                        date = (dayOfMonth + "." + (monthOfYear+1) + "." + year);

                        Snackbar.make(view,date, BaseTransientBottomBar.LENGTH_LONG).show();
                        add_date.setText(date);
                    }
                }, mYear, mMonth, mDay);
        dpd.show();
    }

    //Метод перевірки на пусті поля
    private void ifempty(View view){
        if (TextUtils.isEmpty(add_heading.getText().toString())){
            add_heading.setError(getResources().getString(R.string.req_field));
        }else if (TextUtils.isEmpty(add_description.getText().toString())){

            add_description.setError(getResources().getString(R.string.req_field));
        }else if (TextUtils.isEmpty(add_task_whom.getText().toString())){
            add_task_whom.setError(getResources().getString(R.string.req_field));
        }else if (TextUtils.isEmpty(add_date.getText().toString())){
            add_date.setError(getResources().getString(R.string.req_field));
        }else {
            SaveDB(view);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
    //Метод запису інформації з полів до бази даних
    private void SaveDB(View view){

        Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == 1){
                    onBackPressed();
                }
            }
        };

        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        String dateText = dateFormat.format(currentDate);

        mRef = FirebaseDatabase.getInstance().getReference(TASK);

        heading = add_heading.getText().toString();
        description = add_description.getText().toString();
        task_whom = add_task_whom.getText().toString();

        TaskAdapter taskADD = new TaskAdapter(heading,description,dateText,date,task_whom,false,user.getEmail());

        mRef.push().setValue(taskADD).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
              Snackbar.make(view,getResources().getString(R.string.notif_add_succ), BaseTransientBottomBar.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Snackbar.make(view,getResources().getString(R.string.notif_add_fail), BaseTransientBottomBar.LENGTH_LONG).show();
            }
        });
        handler.sendEmptyMessageDelayed(1, 2500);
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

