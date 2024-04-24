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

public class lvl5 extends AppCompatActivity {
    ImageView imageView, imageView1, life1, life2, life3;
    Button button;
    TextView text, text2, anim, volos;
    private CountDownTimer countDownTimer;
    int k=1, n=0, a=20;
    private DatabaseReference mRef;
    String lvl_key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lvl5);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        imageView=(ImageView)findViewById(R.id.imageView7);
        button=(Button)findViewById(R.id.lvl5_but1);
        text=(TextView) findViewById(R.id.lvl5_text);
        text.setMovementMethod(new ScrollingMovementMethod());
        volos=(TextView)findViewById(R.id.volosy);
        volos.setEnabled(false);
        life1=(ImageView)findViewById(R.id.life5_1);
        life2=(ImageView)findViewById(R.id.life5_2);
        life3=(ImageView)findViewById(R.id.life5_3);
        life1.setVisibility(View.INVISIBLE);
        life2.setVisibility(View.INVISIBLE);
        life3.setVisibility(View.INVISIBLE);
        text2=(TextView)findViewById(R.id.vremia4);
        anim=(TextView)findViewById(R.id.g_o5);
        imageView1=(ImageView)findViewById(R.id.imageView15);
        imageView1.setVisibility(View.INVISIBLE);
        imageView.setEnabled(false);
        text.setText(getResources().getString(R.string.lvl5_text1));
        mRef = FirebaseDatabase.getInstance().getReference("lvl5");
        Intent i = getIntent();
        if (i!=null){
            lvl_key = i.getStringExtra("lvlkey");
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                n++;
                Vibrator vibrator = (Vibrator) getSystemService(v.getContext().VIBRATOR_SERVICE);
                vibrator.vibrate(400);
                text.setText("Ви помилились! Спробуйте ще раз.");
                switch (n){
                    case 1:{life3.setVisibility(View.GONE); a=a-5; break;}
                    case 2:{life2.setVisibility(View.GONE); a=a-5; break;}
                    case 3:{life1.setVisibility(View.GONE); a=a-5; anim.setVisibility(View.VISIBLE);
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
        volos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText(getResources().getString(R.string.lvl5_rezult));
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
                    case 1:{k++; text.setText(getResources().getString(R.string.lvl5_text2)); break;}
                    case 2:{k++;imageView.setImageResource(R.drawable.lvl5_5); text.setText(getResources().getString(R.string.lvl5_text3)); break;}
                    case 3:{k++;imageView.setImageResource(R.drawable.lvl5_2); text.setText(getResources().getString(R.string.lvl5_text4)); break;}
                    case 4:{k++;text.setText(getResources().getString(R.string.lvl5_text5)); break;}
                    case 5:{k++; imageView.setImageResource(R.drawable.lvl5_3);text.setText(getResources().getString(R.string.lvl5_text6)); break;}
                    case 6:{k++;imageView.setImageResource(R.drawable.lvl5_4);text.setText(getResources().getString(R.string.lvl5_text7)); break;}
                    case 7:{k++;text.setText(getResources().getString(R.string.lvl5_text8)); break;}
                    case 8:{k++;text.setText(getResources().getString(R.string.lvl5_text9)); button.setText("Розпочати"); break;}
                    case 9:{k++; imageView1.setVisibility(View.VISIBLE);  life1.setVisibility(View.VISIBLE); imageView.setEnabled(true);

                        life2.setVisibility(View.VISIBLE);
                        life3.setVisibility(View.VISIBLE);
                        volos.setEnabled(true);
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
                    case 10:{zapis();  Intent intent=new Intent("com.example.youngdetective.rezult");
                    startActivity(intent); finish();break;}
                }
            }
        });

    }
    private void zapis() {
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