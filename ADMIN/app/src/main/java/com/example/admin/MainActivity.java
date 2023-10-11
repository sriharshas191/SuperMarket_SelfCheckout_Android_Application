package com.example.admin;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b1=findViewById(R.id.b1);
        b1.setOnClickListener(v ->
        {
            AlertDialog.Builder n=new AlertDialog.Builder(MainActivity.this);
            n.setTitle("Login").setCancelable(false).setPositiveButton("Yes",(dialog,which)-> Toast.makeText(this, "YEs selected", Toast.LENGTH_SHORT).show())
                    .setNegativeButton("No",(dialog,which)-> Toast.makeText(this, "No selectee", Toast.LENGTH_SHORT).show());
//            AlertDialog dialog=new AlertDialog();
            AlertDialog dialog = n.create();
            dialog.show();
        });
    }
}