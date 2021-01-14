package com.example.esp32app.Common.LoginSignUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.esp32app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPwdActivity extends AppCompatActivity implements View.OnClickListener {
TextView mail;
Button btnSend;
FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forgot_pwd);
        mAuth = FirebaseAuth.getInstance();
        mail = findViewById(R.id.txtMail);
        btnSend = findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnSend){
            String email = mail.getText().toString().trim();
            if(email.isEmpty())
            {
                mail.setError("Vui lòng nhập email!");
                mail.requestFocus();
                return;
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                mail.setError("Email không hợp lệ!");
                mail.requestFocus();
                return;
            }
            mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(ForgotPwdActivity.this, "Gửi thành công! Vui lòng kiểm tra email!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(ForgotPwdActivity.this, LoginActivity.class));
                    }
                    else
                        Toast.makeText(ForgotPwdActivity.this, "Gửi thất bại! Vui lòng thử lại!", Toast.LENGTH_LONG).show();
                }
            });
        }
        if (v.getId() == R.id.forgetPwd_login_back_button)
        {
            startActivity(new Intent(ForgotPwdActivity.this, LoginActivity.class));
        }
    }
}