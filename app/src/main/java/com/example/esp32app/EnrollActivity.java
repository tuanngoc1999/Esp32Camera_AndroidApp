package com.example.esp32app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EnrollActivity extends AppCompatActivity {
TextView newName;
Button addFace, home;
DatabaseReference rff = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll);
        newName = findViewById(R.id.txtNewName);
        addFace = findViewById(R.id.btnAddFace);
        home = findViewById(R.id.btnBack_enroll);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EnrollActivity.this, MainActivity.class));
            }
        });
        addFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rff.child("controlStatus").child("doEnrollment").setValue("on");
                String enrollName = newName.getText().toString().trim();
                if(enrollName.isEmpty())
                {
                    newName.setError("Vui lòng nhập tên!");
                    newName.requestFocus();
                }
                else {
                    rff.child("enroll").child("name").setValue(enrollName);
                    Toast.makeText(EnrollActivity.this, "Vui lòng di chuyển mặt lại gần camera!", Toast.LENGTH_LONG).show();
                }

            }
        });
        rff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String status = snapshot.child("enroll").child("status").getValue().toString();
                newName.setText(status);
                if(status.equals("running") )
                    Toast.makeText(EnrollActivity.this, "Đang quét khuôn mặt! Vui lòng giữ camera và khuôn mặt ổn định!", Toast.LENGTH_SHORT).show();
                if(status.equals("NO FACE DETECTED"))
                    Toast.makeText(EnrollActivity.this, "Không phát hiện khuôn mặt! Vui lòng di chuyển mặt lại gần camera!", Toast.LENGTH_SHORT).show();
                if(status.equals("done")) {
                    Toast.makeText(EnrollActivity.this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                    rff.child("controlStatus").child("status").setValue("");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}