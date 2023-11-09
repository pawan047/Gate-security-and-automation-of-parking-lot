package com.example.scanner;

import static android.Manifest.permission_group.CAMERA;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;

import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



public class Scanner extends AppCompatActivity {
   static  final int REQUEST_IMAGE_CAPTURE=1;
   private ImageView captureIV;

    private Bitmap imageBitmap;
   private final TextView resultTV = findViewById(R.id.idTVDetectedText);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
         captureIV=findViewById(R.id.idIVCaptureImage);

        Button snapbtn = findViewById(R.id.idbtnsnap);
        Button detectBtn = findViewById(R.id.idbtnDetect);


        detectBtn.setOnClickListener(v -> detectText());
       snapbtn.setOnClickListener(v -> {
            if(checkPermission()){
                captureImage();
            }
            else{
                requestPermission();
            }
       });
    }

    private void detectText() {
    }

    private  boolean checkPermission(){
        int cameraPermission= ContextCompat.checkSelfPermission(getApplicationContext(),CAMERA);
        return cameraPermission== PackageManager.PERMISSION_GRANTED;

    }
    private void requestPermission(){
        int PERMISSION_CODE=200;
        ActivityCompat.requestPermissions(this,new String[]{CAMERA},PERMISSION_CODE);

    }
    private  void captureImage(){

        Intent takepicture=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takepicture.resolveActivity(getPackageManager())!=null) {
            startActivityForResult(takepicture, REQUEST_IMAGE_CAPTURE);
        } else {
            // Handle the case when there is no app to handle the image capture intent.
            Toast.makeText(this, "No app can handle this action.", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length>0){
            boolean cameraPermission=grantResults[0]==PackageManager.PERMISSION_GRANTED;
            if(cameraPermission){
                Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show();
                captureImage();
            }
            else{
                Toast.makeText(this, "Permission denied..", Toast.LENGTH_SHORT).show();

            }
        }
    }
    



}