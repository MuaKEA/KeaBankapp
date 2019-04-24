package com.example.keabank;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.net.HttpURLConnection;
import java.net.URL;

public class NewCostumer extends AppCompatActivity implements View.OnClickListener {
    EditText emailtxt, passwordtxt, conformpasswordtxt, signmenttext, full_nametxt ,Cpr;
    Button agreementbtn;
    EditText[] editTextsarray;
    String Tag = "NewCostumer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_costumer);
        init();



        agreementbtn.setOnClickListener(this);
    }

    private void init() {
        agreementbtn = findViewById(R.id.donebtn);

        editTextsarray = new EditText[]{findViewById(R.id.Email),
                (EditText) findViewById(R.id.Full_name),
                (EditText) findViewById(R.id.Password),
                (EditText) findViewById(R.id.Conformpassword),
                (EditText) findViewById(R.id.signment),
                (EditText) findViewById(R.id.CPR)};


    }


    @Override
    public void onClick(View v) {
        Intent LoginActivity = new Intent(this, Login.class);

        if (fieldsRequidmentschecker()) {
            Saveuser saveuser= new Saveuser();
            saveuser.execute();
            LoginActivity.putExtra("usernmame", editTextsarray[0].getText().toString());
            LoginActivity.putExtra("password", editTextsarray[2].getText().toString());
            startActivity(LoginActivity);

        }

    }


    public boolean fieldsRequidmentschecker() {
        boolean isallok = true;

        if (emptyfieldschecker()) {
            isallok = false;
        } else if (!editTextsarray[0].getText().toString().contains("@")) {
            editTextsarray[0].setError("@ expected");
            isallok = false;

        } else if (!editTextsarray[2].getText().toString().equals(editTextsarray[3].getText().toString())) {
            editTextsarray[2].setError("Passwords doesn't match");
            editTextsarray[3].setError("Passwords doesn't match");

            isallok = false;
        }else if (editTextsarray[4].getText().toString().length()<=0) {



        isallok = false;
    }

        Log.d(Tag,"condition-->" +isallok);

        return isallok;
    }

    public boolean emptyfieldschecker() {
        boolean check = false;

        for (int i = 0; i < editTextsarray.length; i++) {

            if (editTextsarray[i].getText().length() <= 0) {
                editTextsarray[i].setError("Required Info");
                check=true;

            }
        }

        Log.d(Tag,"emptyfieldschecker-->"+check);

        return check;
    }


    public class Saveuser extends AsyncTask<String, String, String> {

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
            String webapiadress = "http://10.149.88.167:8888/createuser?fullname="+editTextsarray[1].getText().toString()+"&username="+editTextsarray[0].getText().toString() + "&Cpr=" +editTextsarray[5].getText().toString()  +"&password="+editTextsarray[2].getText().toString();
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



