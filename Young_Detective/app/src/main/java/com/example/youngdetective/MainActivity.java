package com.example.youngdetective;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    View fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent intent=new Intent("com.example.youngdetective.vhod_reg");
                startActivity(intent);
            }
        }, 3000); //specify the number of milliseconds



    }

}