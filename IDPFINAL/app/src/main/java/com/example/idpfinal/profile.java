package com.example.idpfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class profile extends AppCompatActivity {
    String username;
    Button b1;
    EditText t1,t2,t3,t4,t5;
    String name,users,mob,email,location;
    DatabaseReference r2;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        UserName user=new UserName();
        username=user.getUser();
        readData(username);
        t1=findViewById(R.id.textView11);
        t2=findViewById(R.id.textView13);
        t3=findViewById(R.id.textView15);
        t4=findViewById(R.id.textView17);
        t5=findViewById(R.id.textView19);
        b1=findViewById(R.id.button4);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kk=new Intent(profile.this,MainHomePage.class);
                startActivity(kk);
            }
        });

    }

    private void readData(String username) {


        r2= FirebaseDatabase.getInstance().getReference("profile");

        r2.child(username).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful())
                {
                    if(task.getResult().exists())
                    {
                        Toast.makeText(profile.this, "Data exists", Toast.LENGTH_SHORT).show();
                        DataSnapshot dataSnapshot = task.getResult();
                        name=String.valueOf(dataSnapshot.child("name").getValue());
                        users=String.valueOf(dataSnapshot.child("username").getValue());
                        mob=String.valueOf(dataSnapshot.child("mobile").getValue());
                        email=String.valueOf(dataSnapshot.child("Email").getValue());
                        location=String.valueOf(dataSnapshot.child("location").getValue());
                        t1.setText(name);
                        t2.setText(mob);
                        t3.setText(email);
                        t4.setText(users);
                        t5.setText(location);





                    }
                    else {
                        Toast.makeText(profile.this, "profile doesn't exists", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(profile.this, "Failed to get data", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}