package com.example.esp32app.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.esp32app.Common.MainActivity;
import com.example.esp32app.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
TextView name, mail, tname;
Button backHome;
private FirebaseAuth mAuth;
DatabaseReference rff = FirebaseDatabase.getInstance().getReference();
String uId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_profile);
        name = findViewById(R.id.txtName_profile);
        tname = findViewById(R.id.txtName_profile_name);
        mail = findViewById(R.id.txtMail_profile);
        uId = mAuth.getCurrentUser().getUid();
        backHome = findViewById(R.id.btnBack);
        backHome.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                finish();
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
                String profileEmail = snapshot.child("Users").child(uId).child("mail").getValue().toString();
                name.setText(profileName);
                mail.setText(profileEmail);
                tname.setText(profileName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }




}