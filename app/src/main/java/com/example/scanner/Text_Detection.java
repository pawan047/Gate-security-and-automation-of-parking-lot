package com.example.scanner;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.util.SparseArray;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Text_Detection extends AppCompatActivity {

    private static final int REQUEST_CODE_PERMISSIONS = 101;
    private final String[] REQUIRED_PERMISSIONS = new String[]{Manifest.permission.CAMERA};
    private ImageCapture imageCapture;
    private TextureView previewView;
    private ExecutorService cameraExecutor;
    private TextRecognizer textRecognizer;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_detection);

        previewView = findViewById(R.id.textureView);
        textView = findViewById(R.id.textView);
        Button captureButton = findViewById(R.id.captureButton);

        if (allPermissionsGranted()) {
            startCamera();
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        }

        cameraExecutor = Executors.newSingleThreadExecutor();
        textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();

        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captureImageAndRecognizeText();
            }
        });
    }

    private boolean allPermissionsGranted() {
        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();

                CameraSelector cameraSelector = new CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build();

                ImageAnalysis imageAnalysis = new ImageAnalysis.Builder().setTargetResolution(new Size(640, 480)).setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST).build();

                imageAnalysis.setAnalyzer(cameraExecutor, new TextAnalyzer());

                Camera camera = cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, imageAnalysis);

            } catch (ExecutionException | InterruptedException e) {
                Log.e("Camera", "Error starting camera: " + e.getMessage());
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private class TextAnalyzer implements ImageAnalysis.Analyzer {
        @Override
        public void analyze(@NonNull ImageProxy imageProxy) {
            TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
            Bitmap bitmap = previewView.getBitmap();

            if (bitmap != null) {
                Frame frame = new Frame.Builder().setBitmap(bitmap).build();
                SparseArray<TextBlock> textBlocks = textRecognizer.detect(frame);

                StringBuilder detectedText = new StringBuilder();
                for (int i = 0; i < textBlocks.size(); i++) {
                    TextBlock textBlock = textBlocks.valueAt(i);
                    detectedText.append(textBlock.getValue()).append("\n");
                }

                final String text = detectedText.toString();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(text);
                    }
                });
            }

            textRecognizer.release();
            imageProxy.close();
        }
    }

    private void captureImageAndRecognizeText() {
        ImageCapture imageCapture = new ImageCapture.Builder().build();

        // Set up the output file for the captured image (you can customize the file location)
        File outputDirectory = getOutputDirectory();
        final File photoFile = new File(outputDirectory, "captured_image.jpg");

        ImageCapture.OutputFileOptions outputFileOptions = new ImageCapture.OutputFileOptions.Builder(photoFile).build();

        imageCapture.takePicture(outputFileOptions, cameraExecutor, new ImageCapture.OnImageSavedCallback() {
            @Override
            public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                // Image has been saved, now recognize text in the image
                recognizeTextInImage(photoFile);
            }

            @Override
            public void onError(@NonNull ImageCaptureException exception) {
                Log.e("Camera", "Error capturing image: " + exception.getMessage());
            }
        });
    }

    private void recognizeTextInImage(File imageFile) {
        if (imageFile != null && imageFile.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            if (bitmap != null) {
                Frame frame = new Frame.Builder().setBitmap(bitmap).build();
                SparseArray<TextBlock> textBlocks = textRecognizer.detect(frame);

                StringBuilder detectedText = new StringBuilder();
                for (int i = 0; i < textBlocks.size(); i++) {
                    TextBlock textBlock = textBlocks.valueAt(i);
                    detectedText.append(textBlock.getValue()).append("\n");
                }

                final String text = detectedText.toString();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(text);
                    }
                });
            } else {
                Log.e("TextRecognition", "Failed to decode the captured image.");
            }
        } else {
            Log.e("TextRecognition", "Image file not found.");
        }
    }

    private File getOutputDirectory() {
        File mediaDir = getExternalMediaDirs()[0];
        if (mediaDir != null) {
            File appDir = new File(mediaDir, "TextRecognitionApp");
            if (!appDir.exists()) {
                if (appDir.mkdirs()) {
                    return appDir;
                } else {
                    Log.e("Camera", "Failed to create app directory.");
                }
            } else {
                return appDir;
            }
        }
        return null;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraExecutor.shutdown();
    }
}
