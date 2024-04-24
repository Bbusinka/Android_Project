package com.example.youngdetective;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Menu extends AppCompatActivity implements View.OnClickListener {
    Button button1, button2, button3, button4, button5, button6;
    ImageButton exit, rezult;
    ImageView imageView;
    String lvl_key, lvl1_key, lvl2_key, lvl3_key, lvl4_key, lvl5_key;
    TextView text;
    private FirebaseUser user; //змінна класу користувача
    private FirebaseAuth mAuth; //змінна класу входу до профіля
    private DatabaseReference mRef;//змінна класу для роботи з базою даних
    public int a=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        user =mAuth.getCurrentUser();
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_menu);
        button1=(Button)findViewById(R.id.but1);
        button2=(Button)findViewById(R.id.but2);
        button3=(Button)findViewById(R.id.but3);
        button4=(Button)findViewById(R.id.but4);
        button5=(Button)findViewById(R.id.but5);
        button6=(Button)findViewById(R.id.but6);
        button2.setEnabled(false);
        button3.setEnabled(false);
        button4.setEnabled(false);
        button5.setEnabled(false);
        button1.setBackgroundColor(getResources().getColor(R.color.but));
        button3.setBackgroundColor(getResources().getColor(R.color.but));
        button2.setBackgroundColor(getResources().getColor(R.color.but));
        button4.setBackgroundColor(getResources().getColor(R.color.but));
        button5.setBackgroundColor(getResources().getColor(R.color.but));
        exit=(ImageButton)findViewById(R.id.exit);
        imageView=(ImageView)findViewById(R.id.imageView8);
        text=(TextView)findViewById(R.id.textView);
        imageView.setVisibility(View.INVISIBLE);
        button6.setVisibility(View.INVISIBLE);
        text.setVisibility(View.INVISIBLE);
        rezult=(ImageButton)findViewById(R.id.rez);
      //  openQuitDialog();
        rezult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.example.youngdetective.rezult");
                startActivity(intent);
            }
        });
        exit.setOnClickListener(this::openSignOutDialog);
        getDataDB();
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            switch (a){
                case 1:{Intent intent=new Intent("com.example.youngdetective.lvl1");
                intent.putExtra("lvlkey",lvl1_key);
                    startActivity(intent); break;}
                case 2:{Intent intent=new Intent("com.example.youngdetective.lvl2");
                    intent.putExtra("lvlkey",lvl2_key);
                    startActivity(intent); break;}
                case 3:{Intent intent=new Intent("com.example.youngdetective.lvl3");
                    intent.putExtra("lvlkey",lvl3_key);
                    startActivity(intent); break;}
                case 4:{Intent intent=new Intent("com.example.youngdetective.lvl4");
                    intent.putExtra("lvlkey",lvl4_key);
                    startActivity(intent); break;}
                case 5:{Intent intent=new Intent("com.example.youngdetective.lvl5");
                    intent.putExtra("lvlkey",lvl5_key);
                    startActivity(intent); break;}
            }}});}
    @Override
    protected void onPause(){
        super.onPause();
        imageView.setVisibility(View.INVISIBLE);
        button6.setVisibility(View.INVISIBLE);
        text.setVisibility(View.INVISIBLE);
        button1.setBackgroundColor(getResources().getColor(R.color.but));
        button3.setBackgroundColor(getResources().getColor(R.color.but));
        button2.setBackgroundColor(getResources().getColor(R.color.but));
        button4.setBackgroundColor(getResources().getColor(R.color.but));
        button5.setBackgroundColor(getResources().getColor(R.color.but));
        getDataDB();
    }

    @Override
    protected void onStart(){
        super.onStart();
        button1.setBackgroundColor(getResources().getColor(R.color.but));
        button3.setBackgroundColor(getResources().getColor(R.color.but));
        button2.setBackgroundColor(getResources().getColor(R.color.but));
        button4.setBackgroundColor(getResources().getColor(R.color.but));
        button5.setBackgroundColor(getResources().getColor(R.color.but));
        getDataDB();
    }

    private void getDataDB(){
    mRef = FirebaseDatabase.getInstance().getReference("lvl1");
    ValueEventListener vListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {

            for (DataSnapshot ds : snapshot.getChildren()) {
                if (user.getEmail().equals(ds.child("user").getValue(String.class))) {
                    lvl1_key=ds.getKey();
                   if(ds.child("status").getValue(Boolean.class)==Boolean.valueOf("true")){
                       button2.setEnabled(true);
                       } } } }
        @Override
        public void onCancelled(@NonNull DatabaseError error) {
        }
    }; mRef.addValueEventListener(vListener);
        mRef = FirebaseDatabase.getInstance().getReference("lvl2");
        ValueEventListener vListener2 = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds : snapshot.getChildren()) {
                    if (user.getEmail().equals(ds.child("user").getValue(String.class))) {
                        lvl2_key=ds.getKey();
                        if(ds.child("status").getValue(Boolean.class)==Boolean.valueOf("true")){
                            button3.setEnabled(true);
                        } } } }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };
        mRef.addValueEventListener(vListener2);
        mRef = FirebaseDatabase.getInstance().getReference("lvl3");
        ValueEventListener vListener3 = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds : snapshot.getChildren()) {
                    if (user.getEmail().equals(ds.child("user").getValue(String.class))) {
                        lvl3_key=ds.getKey();
                        if(ds.child("status").getValue(Boolean.class)==Boolean.valueOf("true")){
                            button4.setEnabled(true);
                        } } } }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };
        mRef.addValueEventListener(vListener3);
        mRef = FirebaseDatabase.getInstance().getReference("lvl4");
        ValueEventListener vListener4 = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds : snapshot.getChildren()) {
                    if (user.getEmail().equals(ds.child("user").getValue(String.class))) {

                        lvl4_key=ds.getKey();
                        if(ds.child("status").getValue(Boolean.class)==Boolean.valueOf("true")){
                            button5.setEnabled(true);
                        } } } }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };
        mRef.addValueEventListener(vListener4);
        mRef = FirebaseDatabase.getInstance().getReference("lvl5");
        ValueEventListener vListener5 = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds : snapshot.getChildren()) {
                    if (user.getEmail().equals(ds.child("user").getValue(String.class))) {

                        lvl5_key=ds.getKey();
                         } } }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };
        mRef.addValueEventListener(vListener5);

}

    /*private void openQuitDialog() {
        AlertDialog.Builder quitDialog = new AlertDialog.Builder(
                this);
        quitDialog.setTitle("Продовжити роботу у даній грі?");

        quitDialog.setPositiveButton("Так", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                imageView.setVisibility(View.INVISIBLE);
                button6.setVisibility(View.INVISIBLE);
                text.setVisibility(View.INVISIBLE);
                getDataDB();
            }
        });

        quitDialog.setNegativeButton("Ні", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                adapter ada=new adapter(0,false,user.getEmail());
                mRef = FirebaseDatabase.getInstance().getReference("lvl1");
                ValueEventListener vListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot ds : snapshot.getChildren()) {
                            if (user.getEmail().equals(ds.child("user").getValue(String.class))) {
                                mRef.push().setValue(ada);
                                } } }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                }; mRef.addValueEventListener(vListener);
                mRef = FirebaseDatabase.getInstance().getReference("lvl2");
                ValueEventListener vListener2 = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot ds : snapshot.getChildren()) {
                            if (user.getEmail().equals(ds.child("user").getValue(String.class))) {
                                mRef.push().setValue(ada);
                                } } }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                };
                mRef.addValueEventListener(vListener2);
                mRef = FirebaseDatabase.getInstance().getReference("lvl3");
                ValueEventListener vListener3 = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot ds : snapshot.getChildren()) {
                            if (user.getEmail().equals(ds.child("user").getValue(String.class))) {
                                mRef.push().setValue(ada);
                                } } }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                };
                mRef.addValueEventListener(vListener3);
                mRef = FirebaseDatabase.getInstance().getReference("lvl4");
                ValueEventListener vListener4 = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot ds : snapshot.getChildren()) {
                            if (user.getEmail().equals(ds.child("user").getValue(String.class))) {
                                mRef.push().setValue(ada);
                                } } }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                };
                mRef.addValueEventListener(vListener4);
                mRef = FirebaseDatabase.getInstance().getReference("lvl5");
                ValueEventListener vListener5 = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot ds : snapshot.getChildren()) {
                            if (user.getEmail().equals(ds.child("user").getValue(String.class))) {
                                mRef.push().setValue(ada);
                            } } }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                };
                mRef.addValueEventListener(vListener5);
            }
        });

        quitDialog.show();
    }*/


    private void openSignOutDialog(View view)  {
        AlertDialog.Builder quitDialog = new AlertDialog.Builder(
                this);
        quitDialog.setTitle("Ви впевнені, що хочите вийти з акаунту?");

        quitDialog.setPositiveButton("Так", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Menu.this,vhod_reg.class);
                startActivity(intent);
            }
        });

        quitDialog.setNegativeButton("Ні", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        quitDialog.show();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.but1:{
                button3.setBackgroundColor(getResources().getColor(R.color.but));
                button2.setBackgroundColor(getResources().getColor(R.color.but));
                button4.setBackgroundColor(getResources().getColor(R.color.but));
                button5.setBackgroundColor(getResources().getColor(R.color.but));
                imageView.setVisibility(View.VISIBLE);
                button1.setBackgroundColor(getResources().getColor(R.color.but_click));
                text.setVisibility(View.VISIBLE);
                button6.setVisibility(View.VISIBLE);
                imageView.setImageResource(R.drawable.lvl1);
                text.setText(getResources().getString(R.string.opis1));
                a=1;
                break;}
            case R.id.but2:{button1.setBackgroundColor(getResources().getColor(R.color.but));
                button3.setBackgroundColor(getResources().getColor(R.color.but));
                button4.setBackgroundColor(getResources().getColor(R.color.but));
                button5.setBackgroundColor(getResources().getColor(R.color.but));
                button2.setBackgroundColor(getResources().getColor(R.color.but_click));
                imageView.setVisibility(View.VISIBLE);
                text.setVisibility(View.VISIBLE);
                button6.setVisibility(View.VISIBLE);
                imageView.setImageResource(R.drawable.lvl2_1);
                text.setText(getResources().getString(R.string.opis2));
                a=2;
                break;}
            case R.id.but3:{
                button1.setBackgroundColor(getResources().getColor(R.color.but));
                button2.setBackgroundColor(getResources().getColor(R.color.but));
                button4.setBackgroundColor(getResources().getColor(R.color.but));
                button5.setBackgroundColor(getResources().getColor(R.color.but));
                button3.setBackgroundColor(getResources().getColor(R.color.but_click));
                imageView.setVisibility(View.VISIBLE);
                text.setVisibility(View.VISIBLE);
                button6.setVisibility(View.VISIBLE);
                imageView.setImageResource(R.drawable.lvl3_1);
                text.setText(getResources().getString(R.string.opis3));
                a=3;
                break;}
            case R.id.but4:{
                button1.setBackgroundColor(getResources().getColor(R.color.but));
                button2.setBackgroundColor(getResources().getColor(R.color.but));
                button3.setBackgroundColor(getResources().getColor(R.color.but));
                button5.setBackgroundColor(getResources().getColor(R.color.but));
                button4.setBackgroundColor(getResources().getColor(R.color.but_click));
                imageView.setVisibility(View.VISIBLE);
                text.setVisibility(View.VISIBLE);
                button6.setVisibility(View.VISIBLE);
                imageView.setImageResource(R.drawable.lvl4_1);
                text.setText(getResources().getString(R.string.opis4));
                a=4;
                break;}
            case R.id.but5:{
                button1.setBackgroundColor(getResources().getColor(R.color.but));
                button2.setBackgroundColor(getResources().getColor(R.color.but));
                button4.setBackgroundColor(getResources().getColor(R.color.but));
                button3.setBackgroundColor(getResources().getColor(R.color.but));
                button5.setBackgroundColor(getResources().getColor(R.color.but_click));
                imageView.setVisibility(View.VISIBLE);
                text.setVisibility(View.VISIBLE);
                button6.setVisibility(View.VISIBLE);
                imageView.setImageResource(R.drawable.lvl5_1);
                text.setText(getResources().getString(R.string.opis5));
                a=5;
                break;}}}}