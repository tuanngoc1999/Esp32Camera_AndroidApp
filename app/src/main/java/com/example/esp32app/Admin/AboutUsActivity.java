package com.example.esp32app.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.esp32app.Common.LoginSignUp.LoginActivity;
import com.example.esp32app.Common.LoginSignUp.RegisActivity;
import com.example.esp32app.R;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;


public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        ImageView btnMem1 = (ImageView) findViewById(R.id.tv_num1);
        ImageView btnMem2 = (ImageView) findViewById(R.id.tv_num2);
        ImageView btnMem3 = (ImageView) findViewById(R.id.tv_num3);
        ImageView btnBack = (ImageView) findViewById(R.id.start_back_btn);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void callScreen(View v) {

        switch (v.getId()){
            case R.id.tv_num1:
                startActivity(new Intent(this, Member1.class));
                break;
            case R.id.tv_num2:
                startActivity(new Intent(this, Member2.class));
                break;
            case R.id.tv_num3:
                startActivity(new Intent(this, Member3.class));
                break;
        }

    }
}