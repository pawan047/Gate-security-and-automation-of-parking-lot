package com.example.scanner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;

import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Locale;
import java.util.concurrent.Executor;

import cameraIntent.RESULT_CODE;

public class login extends AppCompatActivity {
  private   TextToSpeech textToSpeech;
  private static final int  REQUEST_CODE= 25;
  private ImageView imgView;
  private Button btn;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    btn=findViewById(R.id.btnforScan);
    btn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent cameraIntent=new Intent(login.this, camera2.class);
        startActivity(cameraIntent);
      }
    });
    textToSpeech = new TextToSpeech(this,new TextToSpeech.OnInitListener() {
      @Override
      public void onInit(int status) {

        if(status==TextToSpeech.SUCCESS){
                 speak();
                 onDestroy();
        }
        else{
             Toast.makeText(login.this, "voice reader is not working", Toast.LENGTH_SHORT).show();
        }
      }
    });

  }
  private void speak(){
    textToSpeech.setLanguage(Locale.US);
    String str="Scan the Car Number";
    textToSpeech.speak(str,TextToSpeech.QUEUE_FLUSH,null,null);
  }



}
