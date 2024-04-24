package com.example.youngdetective;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class rezult extends AppCompatActivity {
    TextView textView, textView2;
    ImageButton imageButton;
    int g=0;
    private FirebaseUser user; //змінна класу користувача
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rezult);
        textView=(TextView)findViewById(R.id.bal_rezult);
        textView2=(TextView)findViewById(R.id.textView2);
        imageButton=(ImageButton)findViewById(R.id.imageButton);
        mAuth = FirebaseAuth.getInstance();
        user =mAuth.getCurrentUser();
        date();
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        }


private void date(){
    mRef = FirebaseDatabase.getInstance().getReference("lvl1");
    ValueEventListener vListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {

            for (DataSnapshot ds : snapshot.getChildren()) {
                if (user.getEmail().equals(ds.child("user").getValue(String.class))) {

                  adapter ada = ds.getValue(adapter.class);
                   g=g+ada.getBaly();

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
                    adapter ada = ds.getValue(adapter.class);
                    g=g+ada.getBaly();

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
                    adapter ada = ds.getValue(adapter.class);
                    g=g+ada.getBaly();

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
                    adapter ada = ds.getValue(adapter.class);
                    g=g+ada.getBaly();

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
                    adapter ada = ds.getValue(adapter.class);
                    g=g+ada.getBaly();
                    textView.setText(g+"/100");
                    if((g>=50)&&(g<70)){
                        textView2.setText(getResources().getString(R.string.rezult));
                    }
                    if((g>=70)&&(g<90)){
                        textView2.setText(getResources().getString(R.string.rezult2));
                    }
                    if((g>=90)&&(g<=100)){
                        textView2.setText(getResources().getString(R.string.rezult3));
                    }
                } } }
        @Override
        public void onCancelled(@NonNull DatabaseError error) {
        }
    };
    mRef.addValueEventListener(vListener5); }

}