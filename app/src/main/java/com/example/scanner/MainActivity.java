package com.example.scanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
   private  TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button capture = findViewById(R.id.idbtnCapture);
        Button instruction = findViewById(R.id.idReadbtn);
        capture.setOnClickListener(
                v -> {
                    Intent intent = new Intent(MainActivity.this, register.class);
                    startActivity(intent);
                    finish();
                });
        instruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(MainActivity.this,ReadInstruction.class);
                startActivity(it);
                finish();
            }
        });

       }

}