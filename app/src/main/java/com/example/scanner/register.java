package com.example.scanner;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class register extends AppCompatActivity {
   private TextView tv,forgetPassword;
   private Button btn,btnBack;
   private EditText edUsername,edpassword;
    private String username,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btnBack=findViewById(R.id.buttonBack);
        btn=findViewById(R.id.buttonlogin);
        tv=findViewById(R.id.textView3);
        forgetPassword=findViewById(R.id.textViewForgetPassword);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(register.this,main_page.class);
                startActivity(it);
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try{
                            edUsername=findViewById(R.id.editTextLoginUsername);
                            edpassword=findViewById(R.id.editTextLoginPassword);
                       String username = edUsername.getText().toString();
                       String password = edpassword.getText().toString();

                        if (username.equals("pawan") && password.equals("pawan@123")) {
                            Toast.makeText(register.this, "Login Success", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(register.this, main_page.class));
                        } else {
                            Toast.makeText(register.this, "Invalid Credential", Toast.LENGTH_SHORT).show();
                        }
                        }catch (Exception e){
                            Toast.makeText(register.this, "Exception", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(register.this, "Register For New User", Toast.LENGTH_SHORT).show();
              startActivity(new Intent(register.this,new_user.class));
            }
        });
    }


}
