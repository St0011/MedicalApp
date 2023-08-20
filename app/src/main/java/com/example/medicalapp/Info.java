
package com.example.medicalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Info extends AppCompatActivity {
    TextView infotxt1,infotxt2,infotxt3,infotxt4,infotxt5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        infotxt1= findViewById(R.id.infotxt1);
        infotxt2= findViewById(R.id.infotxt2);
        infotxt3= findViewById(R.id.infotxt3);
        infotxt4= findViewById(R.id.infotxt4);
        infotxt5= findViewById(R.id.infotxt5);

        infotxt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Info.this, Dentist.class);
                startActivity(intent);
            }
        });
        infotxt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Info.this, Cardiologist.class);
                startActivity(intent);
            }
        });
        infotxt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Info.this, Alergist.class);
                startActivity(intent);
            }
        });
        infotxt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Info.this, Pediatrist.class);
                startActivity(intent);
            }
        });
        infotxt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Info.this, Articles.class);
                startActivity(intent);
            }
        });


    }
}