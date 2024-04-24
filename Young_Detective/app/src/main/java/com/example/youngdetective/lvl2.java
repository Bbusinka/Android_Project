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

public class lvl2 extends AppCompatActivity {
    ImageView imageView,imageView1, life1, life2, life3;
    Button button;
    TextView text, text2, anim, zena, powar, rab;
    private CountDownTimer countDownTimer;
    private DatabaseReference mRef;
    String lvl_key;
    int k=1, n=0, a=20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lvl2);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        imageView=(ImageView)findViewById(R.id.imageView4);
        imageView1=(ImageView)findViewById(R.id.imageView12);
        imageView1.setVisibility(View.INVISIBLE);
        button=(Button)findViewById(R.id.lvl2_but1);
        text=(TextView) findViewById(R.id.lvl2_text);
        anim=(TextView)findViewById(R.id.g_o2);
        zena=(TextView)findViewById(R.id.zhena);
        powar=(TextView)findViewById(R.id.powar);
        rab=(TextView)findViewById(R.id.domrab);
        rab.setEnabled(false);
        powar.setEnabled(false);
        zena.setEnabled(false);
        life1=(ImageView)findViewById(R.id.life2_1);
        life2=(ImageView)findViewById(R.id.life2_2);
        life3=(ImageView)findViewById(R.id.life2_3);
        life1.setVisibility(View.INVISIBLE);
        life2.setVisibility(View.INVISIBLE);
        life3.setVisibility(View.INVISIBLE);
        text.setMovementMethod(new ScrollingMovementMethod());
        text.setText(getResources().getString(R.string.lvl2_text1));
        text2=(TextView)findViewById(R.id.vremia);
        mRef = FirebaseDatabase.getInstance().getReference("lvl2");
        Intent i = getIntent();
        if (i!=null){
            lvl_key = i.getStringExtra("lvlkey");
        }
        rab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText(getResources().getString(R.string.lvl2_rezult));
                button.setEnabled(true);
                countDownTimer.cancel();
                text2.setVisibility(View.GONE);
                imageView1.setVisibility(View.GONE);
            }
        });
        powar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vibrator = (Vibrator) getSystemService(v.getContext().VIBRATOR_SERVICE);
                vibrator.vibrate(400);
                n++;
                a=a-5;
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
        zena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vibrator = (Vibrator) getSystemService(v.getContext().VIBRATOR_SERVICE);
                vibrator.vibrate(400);
                n++;
                a=a-5;
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
                    case 1:{k++;imageView.setImageResource(R.drawable.lvl2_1); text.setText(getResources().getString(R.string.lvl2_text2)); break;}
                    case 2:{k++;imageView.setImageResource(R.drawable.lvl2_1); text.setText(getResources().getString(R.string.lvl2_text3)); break;}
                    case 3:{k++;imageView.setImageResource(R.drawable.lvl2_2); text.setText(getResources().getString(R.string.lvl2_text4)); break;}
                    case 4:{k++;imageView.setImageResource(R.drawable.lvl2_2); text.setText(getResources().getString(R.string.lvl2_text5));  break;}
                    case 5:{k++;imageView.setImageResource(R.drawable.lvl2_3); text.setText(getResources().getString(R.string.lvl2_text6)); break;}
                    case 6:{k++;imageView.setImageResource(R.drawable.lvl2_2);text.setText(getResources().getString(R.string.lvl2_text7)); button.setText("Розпочати"); break;}
                    case 7:{k++; imageView1.setVisibility(View.VISIBLE); rab.setEnabled(true);
                        life1.setVisibility(View.VISIBLE);
                        life2.setVisibility(View.VISIBLE);
                        life3.setVisibility(View.VISIBLE);
                        powar.setEnabled(true);
                        zena.setEnabled(true);
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
                    case 8:{zapis();finish();break;}
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