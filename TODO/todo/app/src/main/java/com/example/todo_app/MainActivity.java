package com.example.todo_app;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;

import com.example.todo_app.ui.login.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public BottomNavigationView navView;//змінна навігації додатку

    private int mYear, mMonth, mDay; //числові змінні дати
    String date; //текстова змінна з датою

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navView = findViewById(R.id.nav_view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

    }
    //Метод навігації для переходу до наступного вікна
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            this.onBackPressed();
            navView.setVisibility(navView.VISIBLE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //Метод виклику діалогу для виходу з профіля користувача
    private void openSignOutDialog(Context context) {
        AlertDialog.Builder quitDialog = new AlertDialog.Builder(
                this);
        quitDialog.setTitle("Вы уверены что хотите выйти из вашего акаунта?");

        quitDialog.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(context,LoginActivity.class);
                startActivity(intent);
            }
        });

        quitDialog.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        quitDialog.show();
    }
    //Метод який виконує пошук заявок за датою
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
                        Intent intent = new Intent(view.getContext(), DataFindActivity.class);
                        intent.putExtra("finddate",date);
                        startActivity(intent);
                    }
                }, mYear, mMonth, mDay);
        dpd.show();
        Toast.makeText(view.getContext(),"Оберіть дату",Toast.LENGTH_LONG).show();

    }

    //Метод натиску на кнопки
    @Override
    public void onClick(View view) {
        Intent intentadd = new Intent(view.getContext(),AddTaskActivity.class);
        Intent intentpro = new Intent(view.getContext(),ProfileActivity.class);
        switch (view.getId()){
            case R.id.floatingActionButton: startActivity(intentadd);break;
            case R.id.floatingActionButton2:startActivity(intentadd);break;
            case R.id.floatingActionButton3:DateDia(view);break;
            case R.id.floatingActionButton5:DateDia(view);break;
            case R.id.butt_singOut:openSignOutDialog(this);break;
            case R.id.button_profile:startActivity(intentpro);break;
            case R.id.button_about:openAboutDialog();break;
        }
    }
    //Метод виклику діалогу "о програмі"
    private void openAboutDialog() {
        AlertDialog.Builder quitDialog = new AlertDialog.Builder(
                this);
        quitDialog.setTitle(getResources().getString(R.string.about));
        quitDialog.setMessage(getResources().getString(R.string.about_text));

        quitDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        quitDialog.show();
    }
}