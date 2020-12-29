package com.example.esp32app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
TextView name, mail;
Button backHome;
private FirebaseAuth mAuth;
DatabaseReference rff = FirebaseDatabase.getInstance().getReference();
String uId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_profile);
        name = findViewById(R.id.txtName_profile);
        mail = findViewById(R.id.txtMail_profile);
        backHome = findViewById(R.id.btnBack);
        uId = mAuth.getCurrentUser().getUid();
        backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        rff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String profileName = snapshot.child("Users").child(uId).child("name").getValue().toString();
                String profilemail = snapshot.child("Users").child(uId).child("mail").getValue().toString();
                name.setText(profileName);
                mail.setText(profilemail);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}