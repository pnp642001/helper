package com.sgp.helper;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class finger_print extends AppCompatActivity {


    private TextView result;
    private ImageView image;
    private FingerprintManager fingerprintManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger_print);

        result=findViewById(R.id.resultDisplay);
        image=findViewById(R.id.displayImage);

        fingerprintManager=(FingerprintManager)  getSystemService(FINGERPRINT_SERVICE);

        if(!fingerprintManager.isHardwareDetected()){
            result.setText("FingerPrint Scanner is not detected!!");

            Toast.makeText(getApplicationContext(), "YOU CANNOT USE THIS APP!!", Toast.LENGTH_SHORT).show();
        }

        else if(ContextCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT)!= PackageManager.PERMISSION_GRANTED){

            result.setText("Permission Not Granted!!");

            Toast.makeText(getApplicationContext(), "PLEASE GRANT THE PERMISSION TO USE FINGERPRINT", Toast.LENGTH_SHORT).show();
        }

        else if( !fingerprintManager.hasEnrolledFingerprints()){

            result.setText("No FingerPrints Enrolled!!");

            Toast.makeText(getApplicationContext(), "PLEASE REGISTER FINGERPRINT IN YOUR DEVICE!!", Toast.LENGTH_SHORT).show();
        }

        else{

            result.setText("PLACE YOUR FINGER ON SCANNER");

            Toast.makeText(getApplicationContext(), "SCAN YOUR FINGERPRINT!!", Toast.LENGTH_SHORT).show();
            FingerPrintAuthentication check=new FingerPrintAuthentication(this);

            check.authentification(fingerprintManager,null);



        }
    }
}