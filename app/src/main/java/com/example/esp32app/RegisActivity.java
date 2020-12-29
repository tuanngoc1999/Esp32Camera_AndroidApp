package com.example.esp32app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class RegisActivity extends AppCompatActivity implements View.OnClickListener {
    DatabaseReference rff = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth mAuth;
    TextView fullName, email, pwd;
    Button btnRegis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis);
        mAuth = FirebaseAuth.getInstance();
        btnRegis = findViewById(R.id.btnRegis);
        btnRegis.setOnClickListener(this);
        fullName = findViewById(R.id.txtName);
        email = findViewById(R.id.txtMail);
        pwd = findViewById(R.id.txtPwd);
    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btnRegis:
                regisUser();
                break;
        }
    }
    private void regisUser(){
        String name = fullName.getText().toString().trim();
        String mail = email.getText().toString().trim();
        String pass = pwd.getText().toString().trim();
        if(name.isEmpty())
        {
            fullName.setError("Vui lòng nhập họ tên!");
            fullName.requestFocus();
            return;
        }
        if(mail.isEmpty())
        {
            email.setError("Vui lòng nhập email!");
            email.requestFocus();
            return;
        }
        if(pass.isEmpty())
        {
            pwd.setError("Vui lòng nhập mật khẩu!");
            pwd.requestFocus();
            return;
        }
        if(pass.length() < 6)
        {
            pwd.setError("Mật khẩu phải trên 6 ký tự!");
            pwd.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
            email.setError("Vui lòng nhập email hợp lệ!");
            email.requestFocus();
            return;
        }
        mAuth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    rff.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("mail").setValue(mail);
                    rff.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("name").setValue(name).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                                Toast.makeText(RegisActivity.this, "Đăng ký thành công!", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(RegisActivity.this, "Đăng ký không thành công! Vui lòng thử lại!", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else
                    Toast.makeText(RegisActivity.this, "Đăng ký không thành công! Vui lòng thử lại!", Toast.LENGTH_LONG).show();

            }
        });
    }
}