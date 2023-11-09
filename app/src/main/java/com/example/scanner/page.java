package com.example.scanner;

import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.telephony.SmsManager;
import android.widget.EditText;

public class page extends AppCompatActivity {
    private EditText editText1,editText2;
    private  Button btnSendmsg,btnCall;
    private static final int SEND_SMS_PERMISSION_CODE = 101;
    @Override



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);
       btnSendmsg = findViewById(R.id.btnsendmsg);
       btnCall=findViewById(R.id.idmakeCall);
        btnSendmsg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       sendMessage(v);
                    }
                });
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText phoneNumberEditText = findViewById(R.id.phoneNumberEditText);

                String phoneNumber = phoneNumberEditText.getText().toString();

                // Create an Intent to initiate the phone call
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phoneNumber));

                // Check for CALL_PHONE permission before making the call
                if (checkSelfPermission(android.Manifest.permission.CALL_PHONE) != 0) {
                    requestPermissions(new String[]{android.Manifest.permission.CALL_PHONE}, 101);
                } else {
                    startActivity(callIntent);
                }
            }
        });
    }


    public void sendMessage(View view) {
        EditText phoneNumberEditText = findViewById(R.id.phoneNumberEditText);
        EditText messageEditText = findViewById(R.id.messageEditText);

        String phoneNumber = phoneNumberEditText.getText().toString();
        String messageText = messageEditText.getText().toString();

        if (checkPermission()) {
            sendSMS(phoneNumber, messageText);
        } else {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.SEND_SMS},
                    SEND_SMS_PERMISSION_CODE
            );
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
        return ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.SEND_SMS
        ) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SEND_SMS_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
                // You can now send the SMS.
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
