package com.example.keabank;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.HashMap;

public class Currency extends AppCompatActivity implements View.OnClickListener{
Spinner spin1;
Spinner spin2;
HashMap<String, String> countryCodes;
TextView tv;
EditText userInput;
String Ci1;
String Ci2;
Button Calculate;
String Tag = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);
        spin1 = findViewById(R.id.spinner1);
        spin2 = findViewById(R.id.spinner2);
        Calculate = (Button)findViewById(R.id.cal_btn);
        userInput = findViewById(R.id.userinput);
        tv = findViewById(R.id.show);
        Log.d("spin1", spin1.getSelectedItem() +" ");

        countryCodes = new HashMap<>();
        countryCodes.put("Denmark", "DKK");
        countryCodes.put("Sweden", "SEK");
        countryCodes.put("United Stated", "USD");
        countryCodes.put("United Kingdom", "GBP");
        countryCodes.put("Japan", "JPY");
        countryCodes.put("Turkey", "TRY");

        if(savedInstanceState != null){
            tv.setText(savedInstanceState.getString("Result"));
        }

        Calculate.setOnClickListener((View.OnClickListener) this);

    }

    @Override
    public void onClick(View v) {
        Ci1 = countryCodes.get(spin1.getSelectedItem().toString());
        Log.d("Ci1", Ci1);
        Ci2 = countryCodes.get(spin2.getSelectedItem().toString());
        Log.d("Ci2", Ci2);

        ApiCall api = new ApiCall();
    }
}
