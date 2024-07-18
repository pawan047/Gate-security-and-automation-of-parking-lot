package com.example.scanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
    private DataBase dbHelper;
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

                            edUsername=findViewById(R.id.editTextLoginUsername);
                            edpassword=findViewById(R.id.editTextLoginPassword);
                            String username = edUsername.getText().toString();
                            String password = edpassword.getText().toString();
                            DataBase db=new DataBase(getApplicationContext(),"dbms",null,1);
                            if(username.length()==0 || password.length()==0){
                                Toast.makeText(getApplicationContext(),"Please fill all details",Toast.LENGTH_SHORT).show();

                            }else{
                                if(db.login2(username,password)==1){
                                    Toast.makeText(getApplicationContext(),"Login Success",Toast.LENGTH_SHORT).show();
                                    SharedPreferences sharedPreferences=getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor=sharedPreferences.edit();
                                    editor.putString("username",username);
                                    editor.apply();
                                    Intent it=new Intent(register.this,main_page.class);
                                    startActivity(it);
                                }
                                else{
                                    Toast.makeText(getApplicationContext(),"Invalid Username and Password",Toast.LENGTH_SHORT).show();

                                }
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
