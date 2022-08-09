package com.sgp.helper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.Manifest;
import android.app.UiModeManager;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class VirtualAssistant extends AppCompatActivity {

    private SpeechRecognizer recognizer;
    private TextToSpeech textToSpeech;
    private Button btn;
    private TextView text_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virtual_assistant);


        btn=findViewById(R.id.button);


        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.RECORD_AUDIO
                ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                if(report.isAnyPermissionPermanentlyDenied()){

                    Toast.makeText(getApplicationContext(), "ALL PERMISSIONS REQUIRED TO RUN THE APP", Toast.LENGTH_SHORT).show();
                    System.exit(0);
                }
            }
            @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                token.continuePermissionRequest();
            }
        }).check();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent((RecognizerIntent.ACTION_RECOGNIZE_SPEECH));
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,1);
                recognizer.startListening(intent);

            }
        });

        initialize_Text_To_Speech();
        initialize_Result();


    }

    private void initialize_Result(){

        if(!SpeechRecognizer.isRecognitionAvailable(this)){

            Toast.makeText(getApplicationContext(), "SORRY RECOGNIZER NOT THERE", Toast.LENGTH_SHORT).show();
        }

        else{
            recognizer=SpeechRecognizer.createSpeechRecognizer(this);
            recognizer.setRecognitionListener(new RecognitionListener() {
                @Override
                public void onReadyForSpeech(Bundle bundle) {

                }

                @Override
                public void onBeginningOfSpeech() {

                }

                @Override
                public void onRmsChanged(float v) {

                }

                @Override
                public void onBufferReceived(byte[] bytes) {

                }

                @Override
                public void onEndOfSpeech() {

                }

                @Override
                public void onError(int i) {

                }

                @Override
                public void onResults(Bundle bundle) {

                    ArrayList<String> result=bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

                    //Toast.makeText(getApplicationContext(),result.get(0), Toast.LENGTH_SHORT).show();

                    String result_speech=result.get(0).toLowerCase();

                    if(result_speech.contains("camera") || result_speech.contains("open camera")){

                        open_camera();
                    }

                    else if(result_speech.contains("contact") || result_speech.contains("show contacts")|| result_speech.contains("contacts")){

                        open_contacts();
                    }

                    else if(result_speech.contains("open gmail") || result_speech.contains("I want to email") ||
                    result_speech.contains("open email")){

                        open_email();
                    }

                    else if(result_speech.contains("open youtube") || result_speech.contains("youtube")){


                        open_youtube();
                    }

                    else if(result_speech.contains("bluetooth") || result_speech.contains("turn on bluetooth")){
                        activate_bluetooth();
                    }

                    else if(result_speech.contains("music") || result_speech.contains("songs") || result_speech.contains("song")){

                       open_music();
                    }

                    else if(result_speech.contains("current time")){
                        get_current_time();
                    }

                    else if(result_speech.contains("who are you")){

                        speak("Hi! I am HELPER. I am created by Parth Patel,Shruti Patel and Saumya Shah");
                        speak("I am an assistant, whose development is in progress");
                    }

                    else if(result_speech.contains("take notes") || result_speech.contains("note") || result_speech.contains("open note")){

                        open_note_taker();
                    }

                    else if(result_speech.contains("flashlight") || result_speech.contains("activate light") || result_speech.contains("battery")){

                        activate_flash_light();;
                    }

                    else if(result_speech.contains("deactivate light") || result_speech.contains("deactivate flash light")){

                        deactivate_flash_light();
                    }

                    else if(result_speech.contains("who am i")){

                        speak("your highness, you are my master.");
                    }


                    else {

                        speak("Sorry, I cannot process your command.Please Try again.");
                    }





                }

                @Override
                public void onPartialResults(Bundle bundle) {

                }

                @Override
                public void onEvent(int i, Bundle bundle) {

                }
            });
        }
    }



    private void deactivate_flash_light() {

        boolean isFlashLightAvailable=getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT);

        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        String cameraId=null;
        try {
            cameraId = cameraManager.getCameraIdList()[0];
            speak("deactivating the flash light");
            cameraManager.setTorchMode(cameraId,false);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

    }

    private void activate_flash_light() {

        boolean isFlashLightAvailable=getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT);


        if(!isFlashLightAvailable){

            speak("Sorry, your device do not support the flash light feature.");

        }

        else{

             CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
             String cameraId=null;
            try {
                 cameraId = cameraManager.getCameraIdList()[0];
                 speak("activating the flash light");
                cameraManager.setTorchMode(cameraId,true);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }


        }

    }

    private void open_music() {

        speak("opening the music application");
        Intent intent = new Intent(MediaStore.INTENT_ACTION_MUSIC_PLAYER);
        startActivity(intent);
    }

    private void get_current_time(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm:ss");
        String strDate = "Current Time is " + mdformat.format(calendar.getTime());
        speak(strDate);
    }


    private void activate_bluetooth() {


        BluetoothAdapter  bluetoothAdapter= BluetoothAdapter.getDefaultAdapter();

        if(!bluetoothAdapter.isEnabled()){
            speak("turning on bluetooth");
            Intent bluetooth=new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityIfNeeded(bluetooth,1);
        }

        else{
            speak("turning off bluetooth");
                bluetoothAdapter.disable();
        }


    }

    private void open_note_taker() {

        Intent note_taker_intent=new Intent(getApplicationContext(),Note_Taker.class);

        speak("Taking you to the notes section.");
        startActivity(note_taker_intent);
    }


    private void open_youtube() {

        speak("opening YouTube");

        Intent open_youtube_intent= new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/"));
        open_youtube_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        open_youtube_intent.setPackage("com.google.android.youtube");

        startActivity(open_youtube_intent);
    }

    private void open_email() {

        try {
            speak("OPENING EMAIL APP");
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_APP_EMAIL);
            startActivity(intent);
        } catch (android.content.ActivityNotFoundException E) {
            Toast.makeText(getApplicationContext(), "NO EMAIL APP IN YOUR DEVICE.", Toast.LENGTH_SHORT).show();
            speak("SORRY! NO EMAIL APP FOUND");
        }
    }

    private void open_contacts() {

        speak("OPENING CONTACTS");
        Intent contact_intent = new Intent(Intent.ACTION_DEFAULT, ContactsContract.Contacts.CONTENT_URI);
        startActivityIfNeeded(contact_intent, 1);
    }

    private void open_camera() {

        speak("OPENING CAMERA");
        Intent open_camera_intent=new   Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
        startActivity(open_camera_intent);
    }


    private void initialize_Text_To_Speech(){

        textToSpeech=new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(textToSpeech.getEngines().size()==0){
                    Toast.makeText(getApplicationContext(), "ENGINE NOT PRESENT!!", Toast.LENGTH_SHORT).show();
                }

                else {


                    speak("Hello! I am helper! I am here to assist you");




                }
            }
        });
    }

    private void speak(String message){


        textToSpeech.speak(message,TextToSpeech.QUEUE_FLUSH,null,null);
    }


}