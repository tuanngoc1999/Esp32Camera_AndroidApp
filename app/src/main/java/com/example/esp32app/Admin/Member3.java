package com.example.esp32app.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;


import com.example.esp32app.R;

public class Member3 extends AppCompatActivity {

    View btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member3);
        ImageView btnFB = (ImageView)findViewById(R.id.icon_facebook);
        btnFB.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent viewIntent =
                                new Intent("android.intent.action.VIEW",
                                        Uri.parse("https://www.facebook.com/shiro.emon.526/"));
                        startActivity(viewIntent);
                    }
                });
        ImageView btnGit = (ImageView)findViewById(R.id.icon_github);
        btnGit.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent viewIntent =
                                new Intent("android.intent.action.VIEW",
                                        Uri.parse("https://github.com/HoangHieuS"));
                        startActivity(viewIntent);
                    }
                });
        btnBack = findViewById(R.id.aboutUs_back_button);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}