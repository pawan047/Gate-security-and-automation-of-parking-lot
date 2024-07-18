package com.example.scanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class main_page extends AppCompatActivity {
    private Button btn_for_new_car,btn_for_scan_car,btn_for_manually,btn_for_call;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        btn_for_new_car=findViewById(R.id.idbtnNEW_car);
        btn_for_call=findViewById(R.id.idbtnCall_car);
      //  btn_for_manually=findViewById(R.id.idbtnName_manually_car);
        btn_for_scan_car=findViewById(R.id.idbtnScan_car);

        btn_for_new_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startActivity(new Intent(main_page.this,new_vehicle.class));
            }
        });
        btn_for_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startActivity(new Intent(main_page.this,page.class));
            }
        });
        btn_for_scan_car.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(main_page.this,QRCode.class));
                    }
                });

    }
}
