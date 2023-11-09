package com.example.scanner;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;


import android.content.Intent;
import android.provider.MediaStore;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;


public class camera2 extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 101;
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView capturedImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera2);
        Button openCameraButton = findViewById(R.id.captureButton);
        capturedImageView = findViewById(R.id.capturedImageView);
        Button pick=findViewById(R.id.idbtnPick);
        Button btnRecognize=findViewById(R.id.idbtnRecoginze);
        openCameraButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dispatchTakePictureIntent();
                    }
                });
        pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pick an image from the gallery
                //Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //startActivityForResult(intent, PICK_IMAGE_REQUEST);
                openImagePicker();

            }
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // The image is captured, you can process it here
            Uri selectedImageUri = data.getData();
            capturedImageView = findViewById(R.id.capturedImageView);
            // Now, you can use this URI to display or process the selected image.
            // For example, you can set it in an ImageView:
            capturedImageView.setImageURI(selectedImageUri);
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                // Set whitelist for characters

                // Display the captured image in the ImageView
                capturedImageView.setImageBitmap(imageBitmap);
            }
        }
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                capturedImageView.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
