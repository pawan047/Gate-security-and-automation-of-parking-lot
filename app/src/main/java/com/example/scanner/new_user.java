package com.example.scanner;

import androidx.appcompat.app.AppCompatActivity;
import com.example.scanner.DataBase.*;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class new_user extends AppCompatActivity {
    TextView tv;
    EditText edUsername,edpassword,edEmail,edConfirm;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        edUsername=findViewById(R.id.editUsername);
        edpassword=findViewById(R.id.editEnterPassword);
        edConfirm=findViewById(R.id.editConfirmPassword);
        btn=findViewById(R.id.buttonRegister);
        tv=findViewById(R.id.textView3);
        edEmail=findViewById(R.id.editTextTextEmailAddress);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(new_user.this,register.class);
                startActivity(it);
            }
        });
        btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String username = edUsername.getText().toString();
                        String password = edpassword.getText().toString();
                        String email = edEmail.getText().toString();
                        String confirm_password = edConfirm.getText().toString();

                        if (username.length() == 0
                                || password.length() == 0
                                || confirm_password.length() == 0) {
                            Toast.makeText(
                                            getApplicationContext(),
                                            "please Fill all Details",
                                            Toast.LENGTH_SHORT)
                                    .show();

                        } else {
                            if (password.length() < 8) {
                                Toast.makeText(
                                                getApplicationContext(),
                                                "Password must have atleast 8 character",
                                                Toast.LENGTH_SHORT)
                                        .show();
                            }
                            if (password.compareTo(confirm_password) == 0) {
                                if (is_valid(password)) {
                                    Toast.makeText(
                                                    getApplicationContext(),
                                                    "password saved",
                                                    Toast.LENGTH_SHORT)
                                            .show();
                                    startActivity(new Intent(new_user.this, register.class));
                                }
                            } else {
                                Toast.makeText(
                                                getApplicationContext(),
                                                "Password and Confirm Password didn't match",
                                                Toast.LENGTH_SHORT)
                                        .show();
                            }
                        }
                    }
                });
    }
    public static boolean is_valid(String password_here){
        int a1=0,a2=0,a3=0;
        if(password_here.length()==0){
            return false;
        }
        else{
            for (int i=0;i<password_here.length();i++){
                if(Character.isLetter(password_here.charAt(i))){
                    a1=1;
                }
            }
            for (int i=0;i<password_here.length();i++){
                if(Character.isDigit(password_here.charAt(i))){
                    a2=1;
                }
            }
            for (int i=0;i<password_here.length();i++){
                char c=password_here.charAt(i);
                if(c>=33 && c<=46 || c==64){
                    a3=1;
                }
            }

            if(a1==1 && a2==1 && a3==1){
                return true;
            }
            return false;

        }

    }

}
