package com.example.youngdetective;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class vhod_reg extends AppCompatActivity {
    Button but1;
    Button but2;
    EditText uemail, upass;
    View view;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference mRef;
    private FirebaseAuth.AuthStateListener mAuthListener;
    SharedPreferences mSettings;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vhod_reg);
        but1=(Button)findViewById(R.id.vhod);
        but2=(Button)findViewById(R.id.reg);
        uemail=(EditText)findViewById(R.id.email);
        upass=(EditText)findViewById(R.id.pass);
        mAuth = FirebaseAuth.getInstance();
        mSettings = getSharedPreferences("start", Context.MODE_PRIVATE);
        editor = mSettings.edit();
        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               logIn(uemail.getText().toString(),upass.getText().toString());
            }
        });
        but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registration(uemail.getText().toString(),upass.getText().toString());
            }
        });


    }
    public void logIn(String email, String password){

        if (TextUtils.isEmpty(email)){
            uemail.setError(getResources().getString(R.string.error_email));
            return;
        }else if (TextUtils.isEmpty(password)){
            upass.setError(getResources().getString(R.string.error_pass));
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                       editor.putInt("VIEW",1);
                       editor.apply();
                        Toast.makeText(vhod_reg.this,"Вхід виконано успішно", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent("com.example.youngdetective.Menu");
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(vhod_reg.this,"Не вийшло увійти. Перевірте підключення до інтернету.", Toast.LENGTH_SHORT).show();
            }}); }

    private void SaveDB(){
        adapter ada=new adapter(0,false,uemail.getText().toString());

        mRef = FirebaseDatabase.getInstance().getReference("lvl1");


        mRef.push().setValue(ada).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        mRef = FirebaseDatabase.getInstance().getReference("lvl2");


        mRef.push().setValue(ada).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        mRef = FirebaseDatabase.getInstance().getReference("lvl3");


        mRef.push().setValue(ada).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        mRef = FirebaseDatabase.getInstance().getReference("lvl4");


        mRef.push().setValue(ada).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        mRef = FirebaseDatabase.getInstance().getReference("lvl5");


        mRef.push().setValue(ada).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


    }

    public void registration(String email, String password){

        if (TextUtils.isEmpty(email)){
            uemail.setError(getResources().getString(R.string.error_email));
            return;
        }else if (TextUtils.isEmpty(password)){
            upass.setError(getResources().getString(R.string.error_pass));
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(vhod_reg.this, "Реєстрація виконана успішно", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent("com.example.youngdetective.Menu");
                        startActivity(intent);
                        SaveDB();
                        finish();
                    }
                }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(vhod_reg.this,"Не вийшло зареєструватись. Перевірте підключення до інтернету.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void onStart() {
        super.onStart();


            if (mSettings.contains("VIEW")) {
                int view1 = mSettings.getInt("VIEW", 0);
                user = mAuth.getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent("com.example.youngdetective.Menu");
                    startActivity(intent);
                    finish();
                }
            }

    }
}