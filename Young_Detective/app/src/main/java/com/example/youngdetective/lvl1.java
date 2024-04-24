package com.example.youngdetective;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Vibrator;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class lvl1 extends AppCompatActivity {
ImageView imageView,imageView1, life1, life2, life3;
Button button;
TextView text, text2, sled, anim;
String lvl_key;
private CountDownTimer countDownTimer;
    private DatabaseReference mRef;//змінна класу для роботи з базою даних
int k=1, n=0, a=20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lvl1);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        imageView=(ImageView)findViewById(R.id.imageView3);
        imageView1=(ImageView)findViewById(R.id.imageView11);
        imageView1.setVisibility(View.INVISIBLE);
        button=(Button)findViewById(R.id.lvl1_but1);
        text=(TextView) findViewById(R.id.lvl1_text);
        text2=(TextView)findViewById(R.id.vremia1);
        life1=(ImageView)findViewById(R.id.life1_1);
        life2=(ImageView)findViewById(R.id.life1_2);
        life3=(ImageView)findViewById(R.id.life1_3);
        sled=(TextView)findViewById(R.id.sled);
        anim=(TextView)findViewById(R.id.g_o);
        text.setText(getResources().getString(R.string.lvl1_text1));
        imageView.setEnabled(false);
        sled.setEnabled(false);
        life1.setVisibility(View.INVISIBLE);
        life2.setVisibility(View.INVISIBLE);
        life3.setVisibility(View.INVISIBLE);
        mRef = FirebaseDatabase.getInstance().getReference("lvl1");
        Intent i = getIntent();
        if (i!=null){
            lvl_key = i.getStringExtra("lvlkey");
        }
        imageView.setOnClickListener(new View.OnClickListener() {
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
                        }, 2000);break;}}

            }
        });
        sled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText(getResources().getString(R.string.lvl1_rezult));
                button.setEnabled(true);
               countDownTimer.cancel();
               text2.setVisibility(View.GONE);
                imageView1.setVisibility(View.GONE);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (k){
                    case 1:{k++;imageView.setImageResource(R.drawable.lvl1_men); text.setText(getResources().getString(R.string.lvl1_text2)); break;}
                    case 2:{k++;imageView.setImageResource(R.drawable.lvl1); text.setText(getResources().getString(R.string.lvl1_text3)); break;}
                    case 3:{k++;text.setText(getResources().getString(R.string.lvl1_text4)); break;}
                    case 4:{k++;text.setText(getResources().getString(R.string.lvl1_text5)); button.setText("Розпочати"); break;}
                    case 5:{k++; sled.setEnabled(true);imageView1.setVisibility(View.VISIBLE); imageView.setEnabled(true);  life1.setVisibility(View.VISIBLE);
                        life2.setVisibility(View.VISIBLE);
                        life3.setVisibility(View.VISIBLE);
                       countDownTimer = new CountDownTimer(60000, 1000)  {

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
                    case 6:{ zapis(); finish(); break;}

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
        });
    }
    }
