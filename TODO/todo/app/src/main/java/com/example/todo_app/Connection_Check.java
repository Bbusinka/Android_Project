package com.example.todo_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.todo_app.ui.login.LoginActivity;

import java.io.IOException;

public class Connection_Check extends AppCompatActivity {

    Button butt;//змінна єлементу кнопки для перевірки стану з'єднання з мережею

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conntection_check);

        butt = (Button) findViewById(R.id.butt_check_connect);
        butt.setOnClickListener(this::ConnOk);
    }
    //Метод перевірки стану з'єднання з мережею
    private void ConnOk(View view){
        if (!isOnline()==false) {
            Toast.makeText(this, "Підключення встановленно!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
        } else Toast.makeText(this, "Помилка! Перевірте підключення до мережі!", Toast.LENGTH_SHORT).show();

    }
    //Метод перевірки стану з'єднання з мережею
    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        }
        catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }

        return false;
    }
}