package com.example.admin;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText email1,pass1;
    Button b1;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email1=findViewById(R.id.user);
        pass1=findViewById(R.id.pass);
        mAuth=FirebaseAuth.getInstance();
        b1=findViewById(R.id.bu);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sigin();
            }
        });

    }

    private void sigin() {
        String email=email1.getText().toString();
        String pass=pass1.getText().toString();
        if(TextUtils.isEmpty(email))
        {
            email1.setError("email can't be empty");
        }
        else if(TextUtils.isEmpty(pass))
        {
            pass1.setError("password can't be empty");
        }
        else {
            mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent new1=new Intent(Login.this,HomePage.class);
                        startActivity(new1);
                    }
                }
            });
        }


    }
}
