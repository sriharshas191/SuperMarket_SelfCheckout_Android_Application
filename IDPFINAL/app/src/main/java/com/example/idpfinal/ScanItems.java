package com.example.idpfinal;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

public class ScanItems extends AppCompatActivity {

    private SurfaceView surfaceView;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    private ToneGenerator toneGen1;
    private TextView barcodeText;
    private String barcodeData;
    DatabaseReference r2,r3;
    int count=1;
    Button b1,b2,b3;
    ImageButton add,sub;
    String username,pname,price;
    String Productn,Price;
TextView t1,t2,t3,t7;
    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_items);
        toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
        surfaceView = findViewById(R.id.surface_view);
        barcodeText = findViewById(R.id.barcode_text);
        initialiseDetectorsAndSources();
        t1 = findViewById(R.id.textView3);
        t2 = findViewById(R.id.textView4);
        t3 = findViewById(R.id.textView6);
        t3.setText(String.valueOf(count));
//        Intent gt = getIntent();
//        username = (String) gt.getSerializableExtra("username");
        UserName user=new UserName();
        username=user.getUser();
        Log.d(" scanitems activity username",user.getUser()+" ");



        t3.setText(String.valueOf(count));
           add=findViewById(R.id.imageButton);
           sub=findViewById(R.id.imageButton2);
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    count++;
                    t3.setText(String.valueOf(count));
                }
            });
            sub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(count<=0)
                    {
                        Toast.makeText(ScanItems.this, "Buy Atleast 1 Product", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        count--;
                        t3.setText(String.valueOf(count));

                    }

                }
            });
            b3=findViewById(R.id.bt1);
            b3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Helper obj = new Helper(pname, price, count, barcodeData, username);

                    r2=FirebaseDatabase.getInstance().getReference("cart");
                    r2.child(username).child(barcodeData).setValue(obj);
                    Toast.makeText(ScanItems.this, "added to cart", Toast.LENGTH_SHORT).show();

                }
            });

    }




    private void readData(String barcodeData) {
//        Toast.makeText(this, "data started", Toast.LENGTH_SHORT).show();
        r2= FirebaseDatabase.getInstance().getReference("product");

        r2.child(barcodeData).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful())
                {
                    if(task.getResult().exists())
                    {
                        Toast.makeText(ScanItems.this, "Data exists", Toast.LENGTH_SHORT).show();
                        DataSnapshot dataSnapshot = task.getResult();
                          pname=String.valueOf(dataSnapshot.child("product name").getValue());
                          price=String.valueOf(dataSnapshot.child("price").getValue());
                        t1.setText(pname);
                        t2.setText(price);



                    }
                    else {
                        Toast.makeText(ScanItems.this, "Product doesn't exists", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(ScanItems.this, "Failed to get data", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    private void initialiseDetectorsAndSources() {

        //Toast.makeText(getApplicationContext(), "Barcode scanner started", Toast.LENGTH_SHORT).show();

        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true) //you should add this feature
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(ScanItems.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(ScanItems.this, new
                                String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
                // Toast.makeText(getApplicationContext(), "To prevent memory leaks barcode scanner has been stopped", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {


                    barcodeText.post(new Runnable() {

                        @Override
                        public void run() {

                            if (barcodes.valueAt(0).email != null) {
                                barcodeText.removeCallbacks(null);
                                barcodeData = barcodes.valueAt(0).email.address;
                                barcodeText.setText(barcodeData);
                                b1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        readData(barcodeData);
                                    }
                                });
                                toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 150);



                            } else {

                                barcodeData = barcodes.valueAt(0).displayValue;
                                barcodeText.setText(barcodeData);
                                toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 150);
                                b1=findViewById(R.id.button2);
                                b1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        readData(barcodeData);

                                    }
                                });


                            }
                        }
                    });

                }
            }
        });
    }



    @Override
    protected void onPause() {
        super.onPause();
//        getSupportActionBar().hide();
        cameraSource.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        getSupportActionBar().hide();
        initialiseDetectorsAndSources();
    }


    public void logout(View view) {
        Intent f2=new Intent(ScanItems.this,MainActivity.class);
        startActivity(f2);
    }
}