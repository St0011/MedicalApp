package com.example.medicalapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Book extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        CardView Bookdent = findViewById(R.id.Bookdent);
        CardView Bookcardiol = findViewById(R.id.Bookcardiol);
        CardView Bookalerg = findViewById(R.id.Bookalerg);
        CardView Bookpediat = findViewById(R.id.Bookpediat);

        Bookdent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Book.this, Dentist.class);
                startActivity(intent);
            }
        });
        Bookcardiol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Book.this, Cardiologist.class);
                startActivity(intent);
            }
        });
        Bookalerg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Book.this, Alergist.class);
                startActivity(intent);
            }
        });
        Bookpediat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Book.this, Pediatrist.class);
                startActivity(intent);
            }
        });



    }
}