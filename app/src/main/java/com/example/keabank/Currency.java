package com.example.keabank;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;

public class Currency extends AppCompatActivity implements View.OnClickListener{
    Spinner spin1;
    Spinner spin2;
    HashMap<String,String> countrycodes1;
    Button calculatebutton;
    TextView show;
    EditText userinput;
    String countryinitials1,countryinitials2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);
        init();

        if(savedInstanceState != null){
            show.setText(savedInstanceState.getString("Result"));
        }



        calculatebutton.setOnClickListener(this);
    }

    private void init() {
        spin1 = findViewById(R.id.spinner);
        spin2 = findViewById(R.id.spinner2);
        calculatebutton=(Button)findViewById(R.id.currentcycal_btn);
        userinput=findViewById(R.id.userinput);
        show=findViewById(R.id.show);
        Log.d("spin1 ",spin1.getSelectedItemId() +" ");


        countrycodes1 = new HashMap<>();
        countrycodes1.put("Denmark","DKK");
        countrycodes1.put("Sweden","SEK");
        countrycodes1.put("United States","USD");
        countrycodes1.put("EURO","GBP");
        countrycodes1.put("Japan","JPY");
        countrycodes1.put("Turkey","TRY");
    }

    @Override
    public void onClick(View v) {
        countryinitials1=countrycodes1.get(spin1.getSelectedItem().toString());
        Log.d("countryinitials1 ",countryinitials1);
        countryinitials2=countrycodes1.get(spin2.getSelectedItem().toString());
        Log.d("countryinitials2 ",countryinitials2);

        apical api=new apical();
        api.execute();


    }


    public class apical extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }

        @Override
        protected String doInBackground(String... strings) {
            String webapiadress = "https://api.exchangeratesapi.io/latest?base=" + countryinitials1 + "&symbols=" + countryinitials1 +","+countryinitials2;


            try {
                Log.d("step 1",webapiadress);
                URL url = new URL(webapiadress);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.connect();
                BufferedReader bf = new BufferedReader(new InputStreamReader(con.getInputStream()));
                JSONObject json=new JSONObject(bf.readLine());
                DecimalFormat df2 = new DecimalFormat(".##");

                double numb1=Double.parseDouble(json.getJSONObject("rates").getString(countryinitials1));
                Log.d("numb1 ", String.valueOf(numb1));

                double numb2=Double.parseDouble(json.getJSONObject("rates").getString(countryinitials2));
                String result=df2.format(numb1 * Double.parseDouble(userinput.getText().toString()) * numb2);
                Log.d("resualt ", result);

                show.setText("fra " + countryinitials1 + " til " + countryinitials2+ "=" + result );

            } catch (Exception e) {

            }

            return null;
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("Result",show.getText().toString());

    }
}



