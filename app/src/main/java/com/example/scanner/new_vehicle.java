package com.example.scanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class new_vehicle extends AppCompatActivity {
       private TextView already_register;
       private Button register_button;
       private EditText vehicle_number;
       private EditText phone_number;
       private EditText owner_name;
       private EditText address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_vehicle);
        already_register=findViewById(R.id.Textview_already_registered);
        register_button=findViewById(R.id.button_Register);




        already_register.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 startActivity(new Intent(new_vehicle.this,main_page.class));
            }
        });
        register_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        vehicle_number = findViewById(R.id.editextvehicle_number);
                        phone_number = findViewById(R.id.editTextphone_number);
                        owner_name = findViewById(R.id.editTextOwnerName);
                        address = findViewById(R.id.editTextaddress);
                        // databse object
                        DataBase db = new DataBase(getApplicationContext(), "dbms", null, 1);
                        String veh_number = vehicle_number.getText().toString();
                        String phone = phone_number.getText().toString();
                        String ownerName = owner_name.getText().toString();
                        String address_owner = address.getText().toString();

                        if (veh_number.length() == 0
                                || phone.length() == 0
                                || ownerName.length() == 0
                                || address_owner.length() == 0) {
                            Toast.makeText(
                                            getApplicationContext(),
                                            "please fill all details",
                                            Toast.LENGTH_SHORT)
                                    .show();

                        } else if (phone_number.length() != 10) {
                            Toast.makeText(
                                            getApplicationContext(),
                                            "please re-enter mobile number",
                                            Toast.LENGTH_SHORT)
                                    .show();
                        } else if (!is_valid(veh_number)) {
                            Toast.makeText(
                                            getApplicationContext(),
                                            "Car Number is not valid ",
                                            Toast.LENGTH_SHORT)
                                    .show();
                        } else {
                            db.addCar(veh_number, phone, ownerName, address_owner);
                            Toast.makeText(
                                            getApplicationContext(),
                                            "detail saved",
                                            Toast.LENGTH_SHORT)
                                    .show();
                            startActivity(new Intent(new_vehicle.this,main_page.class));
                        }
                    }
                });
    }
    public static boolean is_valid(String vehicle){
        int a=0,b=0;
        if(vehicle.length()!=10){
            return false;
        }
        else{
            for (int i = 0; i < vehicle.length(); i++) {
                if (Character.isLetter(vehicle.charAt(i))) {
                    a++;
                }
            }
            for (int i = 0; i < vehicle.length(); i++) {
                if (Character.isDigit(vehicle.charAt(i))) {
                    b++;
                }
            }
        }

        if(a==4 && b==6){
            return true;
        }
        return  false;
    }
}
