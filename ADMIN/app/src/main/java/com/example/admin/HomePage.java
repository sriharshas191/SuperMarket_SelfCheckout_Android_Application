package com.example.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class HomePage extends AppCompatActivity {
    Button b1;
    Button b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        b1=findViewById(R.id.button2);
        b2=findViewById(R.id.button3);
        b1.setOnClickListener(view -> {
            Intent i1 = new Intent(HomePage.this, AddNewProduct.class);
            startActivity(i1);
        });
        b2.setOnClickListener(view -> {
            Intent i2 = new Intent(HomePage.this, UpdateProducts.class);
            startActivity(i2);
        });

    }
}