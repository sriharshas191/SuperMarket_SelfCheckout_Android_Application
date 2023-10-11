package com.example.idpfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CartAct extends AppCompatActivity {

    private static final String CHANNEL_ID = "channel_id01";
    public static final int NOTIFICATION_ID = 1;
    BottomNavigationView bottomNavigationView;
    Button b1,b2;
    String username;
    RecyclerView recyclerView;
    DatabaseReference r2,r3;
    MyAdapter myAdapter;
    ArrayList<products> list;
    TextView t1,t2,t3;
int totalprice=0,newtotal;
String s1,s2,s3;
int s5;
    int s4;
    int s6;
    String s7;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        b1=findViewById(R.id.button);
        b2=findViewById(R.id.button3);



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel chnnelID = new NotificationChannel("Notification", "My notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(chnnelID);
        }
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(CartAct.this, "Notification")
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle("Payment Notification")
                        .setContentText("Your payment Rs."+s7+" has received")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setAutoCancel(true);
//                Intent i = new Intent(MainActivity.this, MainActivity2.class);
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                i.putExtra("Message", message);
//                PendingIntent p = PendingIntent.getActivity(MainActivity.this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
//                builder.setContentIntent(p);
                NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                manager.notify(1, builder.build());


                r3=FirebaseDatabase.getInstance().getReference("cart");
                r3.child(username).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {

                            Intent ne1=new Intent(CartAct.this,CartAct.class);
                            Toast.makeText(CartAct.this, "Payment Successfull", Toast.LENGTH_SHORT).show();
                            startActivity(ne1);
                        }
                        else {
                            Toast.makeText(CartAct.this, "Payment Unsuccessfull", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });





        UserName user=new UserName();
        username=user.getUser();
        recyclerView=findViewById(R.id.productlist);
        r2= FirebaseDatabase.getInstance().getReference("cart").child(username);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list=new ArrayList<>();
        myAdapter=new MyAdapter(this,list);
        recyclerView.setAdapter(myAdapter);

        r2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren())

                {
                    products pro=dataSnapshot.getValue(products.class);
                    s3=pro.price;
                    s1=pro.quantity;

                    s4=Integer.parseInt(s3);
                    s5=Integer.parseInt(s1);
                    s6=(s4*s5);

                    totalprice=totalprice+s6;
                    s7=String.valueOf(totalprice);
                    t1=findViewById(R.id.textView8);
                    t1.setText(s7);
                    list.add(pro);


                }
                myAdapter.notifyDataSetChanged();


            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        bottomNavigationView = findViewById(R.id.bottomNavigationView);


        bottomNavigationView.setSelectedItemId(R.id.cart);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.cart:
                        return true;

                    case R.id.scan:
                        startActivity(new Intent(getApplicationContext(), MainHomePage.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });


    }

    public void logout(View view) {
        Intent f3=new Intent(CartAct.this,MainActivity.class);
        startActivity(f3);
    }
}