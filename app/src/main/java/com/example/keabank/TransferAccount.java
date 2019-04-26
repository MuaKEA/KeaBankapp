package com.example.keabank;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import java.net.HttpURLConnection;
import java.net.URL;
public class TransferAccount extends AppCompatActivity  {
    EditText[] editTextsarray;
    String Tag = "NewTransfer";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_account);
    }

    public class SaveAccount extends AsyncTask<String, String, String> {

    @Override
    protected String doInBackground(String... strings){
        String webapiadress = "http://10.149.88.167:8888/createuser?fullname="+editTextsarray[1].getText().toString()+"&username="+editTextsarray[0].getText().toString() + "&Cpr="+editTextsarray[5].getText().toString()
                +"&password="+editTextsarray[2].getText().toString();

        String reponse = "";

        URL url;

        try {
            url = new URL(webapiadress);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.connect();
            reponse = String.valueOf(con.getResponseCode());
            Log.d(Tag, reponse);

        } catch (Exception e) {
            e.printStackTrace();


        }

        return null;
    }


        }
}
