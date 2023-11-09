package com.example.scanner;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.firebase.FirebaseApp;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

public class text_scanner extends AppCompatActivity {

    private SurfaceView cameraView;
    private TextView textView;
    private TextRecognizer textRecognizer;
    private CameraSource cameraSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_scanner);
        FirebaseApp.initializeApp(this);

        cameraView = findViewById(R.id.cameraView);
        textView = findViewById(R.id.textView);

        // Request camera permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
        }

        // Create a TextRecognizer
        textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);

        // Set up the camera source
        cameraSource = new CameraSource.Builder((Context) this, (Detector<?>) textRecognizer).setAutoFocusEnabled(true).build();

        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    cameraSource.start(holder);
                } catch (Exception e) {
                    Log.e("Camera", "Failed to start camera source: " + e.getMessage());
                }
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
                // Handle changes to the camera preview
            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
                cameraSource.stop();
            }
        });
    }

    // Handle camera permission request result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, start camera
                cameraView.getHolder().addCallback(cameraCallback);
            } else {
                // Permission denied
                Log.d("Permission", "Camera permission denied.");
            }
        }
    }

    private final SurfaceHolder.Callback cameraCallback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(@NonNull SurfaceHolder holder) {
            try {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                cameraSource.start(holder);
            } catch (Exception e) {
                Log.e("Camera", "Failed to start camera source: " + e.getMessage());
            }
        }

        @Override
        public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
            // Handle changes to the camera preview
        }

        @Override
        public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
            cameraSource.stop();
        }
    };

    // Implement text recognition and display the results in the TextView
    private void processTextRecognitionResult(Text results) {
        if (results != null) {
            StringBuilder recognizedText = new StringBuilder();
            for (Text.TextBlock block : results.getTextBlocks()) {
                for (Text.Line line : block.getLines()) {
                    for (Text.Element element : line.getElements()) {
                        recognizedText.append(element.getText()).append(" ");
                    }
                }
            }
            textView.setText(recognizedText.toString());
        } else {
            textView.setText("No text recognized.");
        }
    }
}
