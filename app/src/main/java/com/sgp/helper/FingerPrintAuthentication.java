package com.sgp.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.widget.TextView;

import androidx.core.content.ContextCompat;


public class FingerPrintAuthentication extends FingerprintManager.AuthenticationCallback {

    private Context context;

    public FingerPrintAuthentication(Context context){

        this.context=context;
    }

    public void authentification(FingerprintManager fingerprintManager,FingerprintManager.CryptoObject cryptoObject){

        CancellationSignal cancellationSignal=new CancellationSignal();
        fingerprintManager.authenticate(cryptoObject,cancellationSignal,0,this,null);


    }

    private void update(String str, boolean status){

        TextView textView=(TextView) ((Activity)context).findViewById(R.id.resultDisplay);

        textView.setText(str);

        if(status==false){

            textView.setBackgroundColor(ContextCompat.getColor(context,R.color.red));
            textView.setTextColor(ContextCompat.getColor(context,R.color.white));

        }

        else{
//            textView.setBackgroundColor(ContextCompat.getColor(context,R.color.green));
//            textView.setTextColor(ContextCompat.getColor(context,R.color.white));

            Intent intent=new Intent(context.getApplicationContext(),VirtualAssistant.class);
            context.startActivity(intent);
        }
    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {

        this.update("OOPS! SOME ERROR OCCURED.\n"+errString,false);
    }

    @Override
    public void onAuthenticationFailed() {

        this.update("AUTHENTIFICATION FAILED!!",false);
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {

        this.update(helpString.toString(),false);
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {

        this.update("AUTHENTIFICATION SUCCESSFUL!!",true);
    }
}
