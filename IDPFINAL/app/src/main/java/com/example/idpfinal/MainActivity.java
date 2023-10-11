package com.example.idpfinal;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    EditText loginUsername,loginPassword;
    Button b1;
    DatabaseReference r2;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginUsername=findViewById(R.id.user);
        loginPassword=findViewById(R.id.pass);
       r2= FirebaseDatabase.getInstance().getReference("profile");
        b1=findViewById(R.id.bu);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateUsername() | !validatePassword()) {
                }
                else
                {
                    pd= new ProgressDialog(MainActivity.this);
                    pd.setMessage("Loading..."); // Setting Message
                    pd.setTitle("Logging in"); // Setting Title
                    pd.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                    pd.show(); // Display Progress Dialog
                    pd.setCancelable(false);
                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                Thread.sleep(1000);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            pd.dismiss();
                        }
                    }).start();
                    checkUser();
                }

            }
        });
    }

    public Boolean validateUsername() {
        String val = loginUsername.getText().toString();
        if (val.isEmpty()) {
            loginUsername.setError("Username cannot be empty");
            return false;
        } else {
            loginUsername.setError(null);
            return true;
        }
    }
    public Boolean validatePassword(){
        String val = loginPassword.getText().toString();
        if (val.isEmpty()) {
            loginPassword.setError("Password cannot be empty");
            return false;
        } else {
            loginPassword.setError(null);
            return true;
        }
    }



    public void register(View view) {
        Intent k2=new Intent(MainActivity.this,RegisterUser.class);
        startActivity(k2);
    }
    public void checkUser(){
        String userUsername = loginUsername.getText().toString().trim();
        String userPassword = loginPassword.getText().toString().trim();
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("profile");
        Query checkUserDatabase = r2.orderByChild("mobile").equalTo(userUsername);
        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    loginUsername.setError(null);
                    String passwordFromDB = snapshot.child(userUsername).child("password").getValue(String.class);
                    if (passwordFromDB.equals(userPassword)) {
                        loginUsername.setError(null);
//                        String nameFromDB = snapshot.child(userUsername).child("name").getValue(String.class);
//                        String emailFromDB = snapshot.child(userUsername).child("email").getValue(String.class);
//                        String usernameFromDB = snapshot.child(userUsername).child("username").getValue(String.class);
//                        String mobilenoFromDB = snapshot.child(userUsername).child("mob").getValue(String.class);
//                        String locationFromDB = snapshot.child(userUsername).child("loc").getValue(String.class);
                        Intent intent = new Intent(MainActivity.this, MainHomePage.class);
                        UserName user = new UserName(userUsername);

                        startActivity(intent);
                    } else {
                        loginPassword.setError("Invalid Credentials");
                        loginPassword.requestFocus();
                    }
                } else {
                    loginUsername.setError("User does not exist");
                    loginUsername.requestFocus();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }





}