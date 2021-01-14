package com.example.esp32app.Common.LoginSignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;

import com.example.esp32app.Admin.AboutUsActivity;
import com.example.esp32app.R;


public class StartScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start_screen);
    }


    public void callScreen(View v) {

        switch (v.getId()){
            case R.id.login_btn:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.signUp_btn:
                startActivity(new Intent(this, RegisActivity.class));
                break;
            case R.id.aboutUs_btn:
                startActivity(new Intent(this, AboutUsActivity.class));
                break;
        }

    }



}