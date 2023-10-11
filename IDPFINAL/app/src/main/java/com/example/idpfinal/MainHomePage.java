package com.example.idpfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainHomePage extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home_page);
        UserName user=new UserName();
        username=user.getUser();
//        Intent dat=getIntent();
//        username= (String) dat.getSerializableExtra("mob");


        bottomNavigationView = findViewById(R.id.bottomNavigationView);


        bottomNavigationView.setSelectedItemId(R.id.scan);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.cart:
                        startActivity(new Intent(getApplicationContext(), CartAct.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.scan:
                        return true;
                }
                return false;
            }
        });
    }
    public void sc(View view)
    {
        Intent jj=new Intent(MainHomePage.this,ScanItems.class);
        jj.putExtra("username",username);
        startActivity(jj);
//        startActivity(new Intent(this,ScanItems.class));
    }


    public void logout(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainHomePage.this);
        builder.setTitle("Logout");
        builder.setMessage("Do you want to logout ?");
        builder.setCancelable(false);
        builder.setPositiveButton("YES",(DialogInterface.OnClickListener) (dialog, which) ->
        {
            Toast.makeText(MainHomePage.this,"You will be logging out...",Toast.LENGTH_SHORT).show();
            Intent f1=new Intent(MainHomePage.this,MainActivity.class);
            startActivity(f1);
            finish();
        });

        builder.setNegativeButton("NO",(DialogInterface.OnClickListener) (dialog,which) ->
        {
            Toast.makeText(MainHomePage.this,"Welcome back!",Toast.LENGTH_SHORT).show();
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }

    public void profile(View view) {
        Intent nn=new Intent(MainHomePage.this,profile.class);
        startActivity(nn);
    }

    public void delete(View view) {
            Intent k3=new Intent(MainHomePage.this,DeleteProduct.class);
            startActivity(k3);
    }
}