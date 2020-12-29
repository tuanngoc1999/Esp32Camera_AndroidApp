package com.example.esp32app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    TextView name;
    ImageView imgView;
    Button btn, addFace, profile;
    Uri image_uri;
    DatabaseReference rff = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.txtName);
        //photo = findViewById(R.id.txtPhoto);
        imgView = findViewById(R.id.imgView);
        btn = findViewById(R.id.btn);
        addFace = findViewById(R.id.btnEnroll);
        profile = findViewById(R.id.btnProfile);
        //takePhoto = findViewById(R.id.btnTakePhoto);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rff.child("controlStatus").child("takePhotoBtn").setValue("on");
            }
        });
        addFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, EnrollActivity.class));
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        rff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String faceName = snapshot.child("esp32-cam").child("name").getValue().toString().toUpperCase();
                String photoBase64 = snapshot.child("esp32-cam").child("photo").getValue().toString();
                String data = photoBase64.substring(photoBase64.indexOf(",") +1);
                name.setText(faceName);
                //photo.setText(photoBase64);

                //byte[] decod = com.example.esp32app.Base64.decode(photoBase64, 4);
                byte[] decoderString = Base64.decode(data.getBytes(), Base64.DEFAULT);
                Bitmap decoded = BitmapFactory.decodeByteArray(decoderString, 0, decoderString.length);

                imgView.setImageBitmap(decoded);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}