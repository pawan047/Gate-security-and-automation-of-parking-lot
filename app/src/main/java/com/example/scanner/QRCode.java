package com.example.scanner;

import static com.example.scanner.page.SEND_SMS_PERMISSION_CODE;
import static java.nio.file.Files.find;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.Math;
import com.journeyapps.barcodescanner.CaptureActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class QRCode extends AppCompatActivity {

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 200;
    private EditText textViewQrText;
    private TextView alert;
     private Button back;
    private DataBase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode );
        textViewQrText = findViewById(R.id.textViewQrText  );
        back=findViewById(R.id.button_Back);
        db=new DataBase(getApplicationContext(),"dbms",null,1);

        // Request camera permission if not granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_REQUEST_CODE);
        } else {
            // Permission already granted
            startQRScanner();
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QRCode.this,new_vehicle.class));
            }
        });
    }

    // Handle permission request result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                startQRScanner();
            } else {
                // Permission denied
                Toast.makeText(this, "Camera permission is required to scan QR codes",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    // Start QR code scanner
    private void startQRScanner() {
        IntentIntegrator integrator = new IntentIntegrator(this);
       // integrator.setCaptureActivity(QRCode.class); // Custom activity for better customization if needed
        integrator.setOrientationLocked(false); // Optionally lock orientation
        integrator.initiateScan();
    }
    public void sendMessage(View view) {
      //  EditText phoneNumberEditText = findViewById(R.id.phoneNumberEditText);
        EditText messageEditText = findViewById(R.id.messageEditText);

        String phoneNumber = textViewQrText.getText().toString();

        String owner=db.getOwnerName(phoneNumber);
        String  car=db.getCarNumber(phoneNumber);
        //String A="ABC";
      //  int max=20,min=1;
      //  int mx=2,mn=0;
      //  double a = Math.random()*(max-min+1)+min;
      //  int b= (int) (Math.random()*(mx-mn+1)+mn);
        //  int number =
    // String messageText ="Hello "+owner+" Welcome to the NIT Srinagar Campus your vehicle(no."+car+") is to be parked at parking slot  Payment Deducted : 10.00/n available vault balance: 250.00";
     String messageText="Hello "+phoneNumber+" Welcome to NIT Srinagar you car is to be parked at parking A slot 15";
        if (checkPermission()) {
            sendSMS(phoneNumber, messageText);
        } else {
            ActivityCompat.requestPermissions(
                    this, new String[] {Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_CODE);
        }
    }


    private void sendSMS(String phoneNumber, String message) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Toast.makeText(this, "Message sent successfully", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Message sending failed", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private boolean checkPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                == PackageManager.PERMISSION_GRANTED;
    }


    // Handle scan result


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Scan cancelled", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                // Extracted QR code text
                String qrText = result.getContents();
                // Display QR code text in TextView
                textViewQrText.setText(qrText);
                String phoneNumber = textViewQrText.getText().toString();
                //  String messageText ="Hey what's up "+phoneNumber+" Welcome to the NIT Srinagar your vehicle is to be parked at parking A slot 15";
                //DataBase database=new DataBase(getApplicationContext(),"dbms",null,1);
              //  int x=db.isCarPresent(phoneNumber);
                // sendSMS(phoneNumber, messageText);
                // Here you can use 'qrText' as needed (e.g., send it to a server, display it in a TextView, etc.)
            //   if(x==1){
                    View v = null;
                    sendMessage(v);
                    startActivity(new Intent(QRCode.this,main_page.class));
             // } else{
               //  alert=findViewById(R.id.textViewforalert);
               // alert.setText("This car is not registered ");
              //  }

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }



}
