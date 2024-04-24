package com.example.youngdetective;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class lvl4 extends AppCompatActivity {
    ImageView imageView, imageView1, life1, life2, life3;
    Button button;
    TextView text, text2, anim, oleg, maha, dima, lena;
    private CountDownTimer countDownTimer;
    int k=1, n=0, a=20;
    private DatabaseReference mRef;
    String lvl_key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lvl4);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        imageView=(ImageView)findViewById(R.id.imageView6);
        button=(Button)findViewById(R.id.lvl4_but1);
        text=(TextView) findViewById(R.id.lvl4_text);
        text2=(TextView)findViewById(R.id.vremia3);
        imageView1=(ImageView)findViewById(R.id.imageView14);
        imageView1.setVisibility(View.INVISIBLE);
        life1=(ImageView)findViewById(R.id.life4_1);
        life2=(ImageView)findViewById(R.id.life4_2);
        life3=(ImageView)findViewById(R.id.life4_3);
        life1.setVisibility(View.INVISIBLE);
        life2.setVisibility(View.INVISIBLE);
        life3.setVisibility(View.INVISIBLE);
        oleg=(TextView)findViewById(R.id.oleg);
        maha=(TextView)findViewById(R.id.maha);
        dima=(TextView)findViewById(R.id.dima);
        lena=(TextView)findViewById(R.id.lena);
        oleg.setEnabled(false);
        maha.setEnabled(false);
        dima.setEnabled(false);
        lena.setEnabled(false);
        anim=(TextView)findViewById(R.id.g_o4);
        text.setText(getResources().getString(R.string.lvl4_text1));
        mRef = FirebaseDatabase.getInstance().getReference("lvl4");
        Intent i = getIntent();
        if (i!=null){
            lvl_key = i.getStringExtra("lvlkey");
        }
        oleg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText(getResources().getString(R.string.lvl4_rezult));
                button.setEnabled(true);
                countDownTimer.cancel();
                text2.setVisibility(View.GONE);
                imageView1.setVisibility(View.GONE);
            }
        });
        maha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a=a-5;
                Vibrator vibrator = (Vibrator) getSystemService(v.getContext().VIBRATOR_SERVICE);
                vibrator.vibrate(400);
                n++;
                text.setText("Ви помилились! Спробуйте ще раз.");
                switch (n){
                    case 1:{life3.setVisibility(View.GONE); break;}
                    case 2:{life2.setVisibility(View.GONE); break;}
                    case 3:{life1.setVisibility(View.GONE); anim.setVisibility(View.VISIBLE);
                        Animation animation = AnimationUtils.loadAnimation(v.getContext(), R.anim.game_over);
                        anim.startAnimation(animation);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                finish();
                            }
                        }, 4000);break;}}
            }
        });
        dima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a=a-5;
                n++;
                Vibrator vibrator = (Vibrator) getSystemService(v.getContext().VIBRATOR_SERVICE);
                vibrator.vibrate(400);
                text.setText("Ви помилились! Спробуйте ще раз.");
                switch (n){
                    case 1:{life3.setVisibility(View.GONE); break;}
                    case 2:{life2.setVisibility(View.GONE); break;}
                    case 3:{life1.setVisibility(View.GONE); anim.setVisibility(View.VISIBLE);
                        Animation animation = AnimationUtils.loadAnimation(v.getContext(), R.anim.game_over);
                        anim.startAnimation(animation);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                finish();
                            }
                        }, 4000);break;}}
            }
        });
        lena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a=a-5;
                n++;
                Vibrator vibrator = (Vibrator) getSystemService(v.getContext().VIBRATOR_SERVICE);
                vibrator.vibrate(400);
                text.setText("Ви помилились! Спробуйте ще раз.");
                switch (n){
                    case 1:{life3.setVisibility(View.GONE); break;}
                    case 2:{life2.setVisibility(View.GONE); break;}
                    case 3:{life1.setVisibility(View.GONE); anim.setVisibility(View.VISIBLE);
                        Animation animation = AnimationUtils.loadAnimation(v.getContext(), R.anim.game_over);
                        anim.startAnimation(animation);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                finish();
                            }
                        }, 4000);break;}}
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (k){
                    case 1:{k++;imageView.setImageResource(R.drawable.lvl4_2); text.setText(getResources().getString(R.string.lvl4_text2)); break;}
                    case 2:{k++;imageView.setImageResource(R.drawable.lvl4_3); text.setText(getResources().getString(R.string.lvl4_text3)); break;}
                    case 3:{k++;text.setText(getResources().getString(R.string.lvl4_text4)); break;}
                    case 4:{k++;text.setText(getResources().getString(R.string.lvl4_text5)); break;}
                    case 5:{k++; imageView.setImageResource(R.drawable.lvl4_2);text.setText(getResources().getString(R.string.lvl4_text6)); break;}
                    case 6:{k++;text.setText(getResources().getString(R.string.lvl4_text7)); break;}
                    case 7:{k++;text.setText(getResources().getString(R.string.lvl4_text8)); break;}
                    case 8:{k++;text.setText(getResources().getString(R.string.lvl4_text9)); break;}
                    case 9:{k++;text.setText(getResources().getString(R.string.lvl4_text10)); button.setText("Розпочати"); break;}
                    case 10:{k++; imageView1.setVisibility(View.VISIBLE);
                        life1.setVisibility(View.VISIBLE);
                        life2.setVisibility(View.VISIBLE);
                        life3.setVisibility(View.VISIBLE);
                        oleg.setEnabled(true);
                        maha.setEnabled(true);
                        dima.setEnabled(true);
                        lena.setEnabled(true);
                        countDownTimer=new CountDownTimer(60000, 1000) {

                            public void onTick(long millisUntilFinished) {
                                text2.setText("0:"+millisUntilFinished / 1000);
                            }

                            public void onFinish() {
                                anim.setVisibility(View.VISIBLE);
                                Animation animation = AnimationUtils.loadAnimation(v.getContext(), R.anim.game_over);
                                anim.startAnimation(animation);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        finish();
                                    }
                                }, 4000);
                            }
                        }.start();
                        button.setText("Закінчити");
                        button.setEnabled(false);
                        break;}
                    case 11:{zapis();finish();break;}
                }
            }
        });

    }
    private void zapis(){
        mRef.child(lvl_key).child("baly").setValue(a).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        mRef.child(lvl_key).child("status").setValue(true).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });}
}