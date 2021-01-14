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

import com.example.esp32app.Common.MainActivity;
import com.example.esp32app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    TextView forgotPwd, regis, email, pwd;
    Button btnSignIn;

//    CheckBox rememberMe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        regis = findViewById(R.id.txtRegis);
        email = findViewById(R.id.txtMail);
        pwd = findViewById(R.id.txtPwd);
        regis.setOnClickListener(this);
        btnSignIn = findViewById(R.id.btnLogin);
        btnSignIn.setOnClickListener(this);
        forgotPwd = findViewById(R.id.txtForgotPwd);
        forgotPwd.setOnClickListener(this);

//        rememberMe = findViewById(R.id.remember_me);
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.txtRegis:
                signUp();
                break;
            case R.id.btnLogin:
                signIn();
                break;
            case R.id.txtForgotPwd:
                resetPassword();
                break;
            case R.id.login_back_button:
                loginBack();
                break;
        }

    }

    private void loginBack() {
        Intent loginBack = new Intent(this, StartScreen.class);
        startActivity(loginBack);
    }

    private void resetPassword() {
        Intent rsPassIntent = new Intent(this, ForgotPwdActivity.class);
        startActivity(rsPassIntent);
    }

    private void signUp() {
        Intent signUpIntent = new Intent(this, RegisActivity.class);
        startActivity(signUpIntent);
    }

    private void signIn(){
        String mail = email.getText().toString().trim();
        String pass = pwd.getText().toString().trim();
        Intent loginIntent = new Intent(this, MainActivity.class);
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
        mAuth.signInWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(loginIntent);
                }
                else
                    Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_LONG).show();
            }
        });
    }
}