package com.example.todo_app.ui.login;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todo_app.Connection_Check;
import com.example.todo_app.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    SharedPreferences mSettings;//змінна класу з налаштуваннями
    SharedPreferences.Editor editor;//змінна з налаштуваннями для входу

    private FirebaseAuth mAuth;//змінна класу користувача
    private FirebaseUser user;//змінна класу входу до профіля

    public EditText usernameET,passwordET;//змінні елементів для вводу електронної адреси та паролю

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameET = findViewById(R.id.add_heading);
        passwordET = findViewById(R.id.password1);

        mAuth = FirebaseAuth.getInstance();

        mSettings = getSharedPreferences("start", Context.MODE_PRIVATE);
        editor = mSettings.edit();
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
    //Метод перевірки стану з'єднання з мережею
    protected void onStart() {
        super.onStart();

        if (!isOnline()==false) {
            if (mSettings.contains("VIEW")) {
                int view1 = mSettings.getInt("VIEW", 0);
                user = mAuth.getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent("com.example.todo_app.MainActivity");
                    startActivity(intent);
                    finish();
                }
            }
        }else{
            Intent intent = new Intent(this, Connection_Check.class);
            startActivity(intent);
            }
    }

    @Override
    public void onBackPressed() {
        openQuitDialog();
    }
    //Метод діалогу "Вихід з додутку"
    private void openQuitDialog() {
        AlertDialog.Builder quitDialog = new AlertDialog.Builder(
                this);
        quitDialog.setTitle(getResources().getString(R.string.dia_exit));

        quitDialog.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        quitDialog.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        quitDialog.show();
    }
    //Метод натисків на кнопки
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login:
                logIn(usernameET.getText().toString(),passwordET.getText().toString());
                break;
            case R.id.registration:
                registration(usernameET.getText().toString(),passwordET.getText().toString());
                break;
        }
    }
    //Метод виконує вхід до профілю користувача
    public void logIn(String email, String password){

        if (TextUtils.isEmpty(email)){
            usernameET.setError(getResources().getString(R.string.error_email));
            return;
        }else if (TextUtils.isEmpty(password)){
            passwordET.setError(getResources().getString(R.string.error_pass));
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                editor.putInt("VIEW",1);
                editor.apply();
                Toast.makeText(LoginActivity.this, getResources().getString(R.string.notif_auth_succ), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent("com.example.todo_app.MainActivity");
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this,getResources().getString(R.string.notif_auth_fail), Toast.LENGTH_SHORT).show();
            }
        });

    }
    //Метод який виконує реєстрацію нового користувача
    public void registration(String email, String password){

        if (TextUtils.isEmpty(email)){
            usernameET.setError(getResources().getString(R.string.error_email));
            return;
        }else if (TextUtils.isEmpty(password)){
            passwordET.setError(getResources().getString(R.string.error_pass));
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
        .addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                    Toast.makeText(LoginActivity.this, getResources().getString(R.string.notif_reg_succ), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent("com.example.todo_app.MainActivity");
                    startActivity(intent);
                    finish();
            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, getResources().getString(R.string.notif_reg_fail), Toast.LENGTH_SHORT).show();
            }
        });
    }
}