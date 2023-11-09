package com.example.scanner;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.TextRecognizerOptionsInterface;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

public class detection extends AppCompatActivity {
    private ImageView imageView;
    private Button detectTextButton;
    private TextView detected_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detection);
        imageView = findViewById(R.id.imageView);
        detectTextButton = findViewById(R.id.detectTextButton);
        detected_text=findViewById(R.id.textDisplay);

        detectTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detectTextFromImage();
            }
        });
    }
    private void detectTextFromImage() {
        // Load the image from resources (replace with your own image)
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sample_image);

        // Create an InputImage from the loaded bitmap
        InputImage image = InputImage.fromBitmap(bitmap, 0);

        // Create a TextRecognizer
        TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);

        // Perform text recognition
        recognizer.process(image).addOnSuccessListener(new com.google.android.gms.tasks.OnSuccessListener<Text>() {
                    @Override
                    public void onSuccess(@NonNull Text texts) {
                        // Handle successful text recognition
                        // Extract and display the detected text
                        String detectedText = texts.getText();
                        // You can display the detected text in a TextView or handle it as needed
                        // For this example, we'll simply show it in a toast message
                        showToast(detectedText);
                    }
                })
                .addOnFailureListener(new com.google.android.gms.tasks.OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle text recognition failure
                        showToast("Text detection failed: " + e.getMessage());
                    }
                });
    }

    private void showToast(String messege){
        detected_text.setText(messege);
    }
}
