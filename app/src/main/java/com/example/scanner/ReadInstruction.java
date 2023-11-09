package com.example.scanner;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ReadInstruction extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_instruction);
        TextView instructionsTextView = findViewById(R.id.instructionsTextView);
        String dynamicInstructions = "1.Security Check:Be prepared to show identification, such as a government-issued ID or a visitor's pass. Follow the instructions of security personnel.\n" +
                "\n" +
                "2.Speed Limits: Follow the designated speed limits within the campus. These limits are typically lower than regular road speed limits to ensure the safety of pedestrians and cyclists.\n" +
                "\n" +
                "3.Parking Rules: Park your car in designated parking areas only. Do not block access roads, walkways, or fire exits. Observe any parking restrictions and pay attention to no-parking zones.\n\n"+
                "4.No Unauthorized Entry: Do not attempt to enter restricted areas, research labs, or hostels without proper authorization. Unauthorized entry can lead to security concerns and disciplinary actions.\n\n"+
                "Always remember that Our campus are educational environments where the focus is on learning and research. Respecting campus rules and being considerate of others is not only a matter of etiquette but also contributes to a positive and safe campus experience for everyone!";
        instructionsTextView.setText(dynamicInstructions);
        Button back=findViewById(R.id.idbtnback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(ReadInstruction.this,MainActivity.class);
                startActivity(it);
                finish();
            }
        });
    }


}
