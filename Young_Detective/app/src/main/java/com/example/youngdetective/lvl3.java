package com.example.youngdetective;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.text.method.ScrollingMovementMethod;
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

public class lvl3 extends AppCompatActivity {
    ImageView imageView, imageView2, life1, life2, life3;
    Button button;
    TextView text, text2, anim, rab, pochta, sosed;
    private CountDownTimer countDownTimer;
    int k=1, n=0,a=20;
    private DatabaseReference mRef;
    String lvl_key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lvl3);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        imageView=(ImageView)findViewById(R.id.imageView5);
        button=(Button)findViewById(R.id.lvl3_but1);
        text=(TextView) findViewById(R.id.lvl3_text);
        text.setMovementMethod(new ScrollingMovementMethod());
        text2=(TextView)findViewById(R.id.vremia2);
        imageView2=(ImageView)findViewById(R.id.imageView13);
        imageView2.setVisibility(View.INVISIBLE);
        anim=(TextView)findViewById(R.id.g_o3);
        rab=(TextView)findViewById(R.id.domr);
        pochta=(TextView)findViewById(R.id.pochta);
        sosed=(TextView)findViewById(R.id.sosed);
        life1=(ImageView)findViewById(R.id.life3_1);
        life2=(ImageView)findViewById(R.id.life3_2);
        life3=(ImageView)findViewById(R.id.life3_3);
        life1.setVisibility(View.INVISIBLE);
        life2.setVisibility(View.INVISIBLE);
        life3.setVisibility(View.INVISIBLE);
        rab.setEnabled(false);
        pochta.setEnabled(false);
        sosed.setEnabled(false);
        text.setText(getResources().getString(R.string.lvl3_text1));
        mRef = FirebaseDatabase.getInstance().getReference("lvl3");
        Intent i = getIntent();
        if (i!=null){
            lvl_key = i.getStringExtra("lvlkey");
        }
        rab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText(getResources().getString(R.string.lvl3_rezult));
                button.setEnabled(true);
                countDownTimer.cancel();
                text2.setVisibility(View.GONE);
                imageView2.setVisibility(View.GONE);
            }
        });
        pochta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vibrator = (Vibrator) getSystemService(v.getContext().VIBRATOR_SERVICE);
                vibrator.vibrate(400);
                a=a-5;
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
        sosed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vibrator = (Vibrator) getSystemService(v.getContext().VIBRATOR_SERVICE);
                vibrator.vibrate(400);
                a=a-5;
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
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (k){
                    case 1:{k++;imageView.setImageResource(R.drawable.lvl3_2); text.setText(getResources().getString(R.string.lvl3_text2)); break;}
                    case 2:{k++;imageView.setImageResource(R.drawable.lvl3_3); text.setText(getResources().getString(R.string.lvl3_text3)); break;}
                    case 3:{k++;imageView.setImageResource(R.drawable.lvl3_4);text.setText(getResources().getString(R.string.lvl3_text4)); break;}
                    case 4:{k++;imageView.setImageResource(R.drawable.lvl3_6);text.setText(getResources().getString(R.string.lvl3_text5)); break;}
                    case 5:{k++;text.setText(getResources().getString(R.string.lvl3_text6)); break;}
                    case 6:{k++;text.setText(getResources().getString(R.string.lvl3_text7)); break;}
                    case 7:{k++; text.setText(getResources().getString(R.string.lvl3_text8)); button.setText("Розпочати"); break;}
                    case 8:{k++; imageView2.setVisibility(View.VISIBLE); life1.setVisibility(View.VISIBLE);
                        life2.setVisibility(View.VISIBLE);
                        life3.setVisibility(View.VISIBLE);
                        rab.setEnabled(true);
                        pochta.setEnabled(true);
                        sosed.setEnabled(true);
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
                    case 9:{zapis();finish();break;}
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