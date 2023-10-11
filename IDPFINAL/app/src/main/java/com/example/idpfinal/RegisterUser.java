package com.example.idpfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity {
    EditText e1,e2,e3,e4,e5,e6,e7;
    Button reg;
    FirebaseAuth mAuth;
    DatabaseReference r2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        mAuth=FirebaseAuth.getInstance();

        e1=findViewById(R.id.editTextTextPersonName);
        e2=findViewById(R.id.editTextTextPersonName2);
        e3=findViewById(R.id.editTextTextPersonName3);
        e4=findViewById(R.id.editTextTextPersonName4);
        e5=findViewById(R.id.editTextTextPersonName5);
        e6=findViewById(R.id.editTextTextPersonName6);
        e7=findViewById(R.id.editTextTextPersonName7);
        reg=findViewById(R.id.button);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });
    }

    private void createAccount() {

        String email = e4.getText().toString();
        String password = e5.getText().toString();
        String username=e1.getText().toString();
        String lname=e2.getText().toString();
        String mno=e3.getText().toString();
        String location=e6.getText().toString();
        String pin=e7.getText().toString();
        r2= FirebaseDatabase.getInstance().getReference("profile");
        r2.child(mno).child("name").setValue(lname);
        r2.child(mno).child("username").setValue(username);

        r2.child(mno).child("mobile").setValue(mno);
        r2.child(mno).child("Email").setValue(email);
        r2.child(mno).child("password").setValue(password);
        r2.child(mno).child("location").setValue(location);
        r2.child(mno).child("pincode").setValue(pin);
        Toast.makeText(this, "Registered Succesful", Toast.LENGTH_SHORT).show();
        Intent k1=new Intent(RegisterUser.this,MainActivity.class);
        startActivity(k1);







//        if(TextUtils.isEmpty(email)){
//            e4.setError("Email cannot be empty");
//            e4.requestFocus();
//        }
//        else if(TextUtils.isEmpty(password)){
//            e5.setError("Password cannot be Empty");
//            e5.requestFocus();
//        }
//        else {
//            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                @Override
//                public void onComplete(@NonNull Task<AuthResult> task) {
//                    if(task.isSuccessful()){
//                        Intent k1=new Intent(RegisterUser.this,MainActivity.class);
//                        Toast.makeText(RegisterUser.this,"email and password Registered",Toast.LENGTH_SHORT).show();
//                        startActivity(k1);
//                    }
//                    else
//                    {
//                        Toast.makeText(RegisterUser.this,"Registration failed",Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//        }
    }
}