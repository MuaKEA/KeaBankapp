package com.example.keabank;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;


public class CreateAccount extends AppCompatActivity implements View.OnClickListener {
Button applybtn;
Spinner accountype;
EditText accountname;
String Email ,ip;
String Tag = "CreateAccount";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);


        Getvaluesfromsharedpref();
        startup();

        applybtn.setOnClickListener(this);
    }

    private void startup() {
    applybtn = findViewById(R.id.applybtn);
    accountname=findViewById(R.id.accountname);
    accountype=findViewById(R.id.accountype);


    }

    private void Getvaluesfromsharedpref() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        Email=pref.getString("username","");
        ip=pref.getString("Ip","");
        Log.d(Tag,Email + "<---Email from shared");
        Log.d(Tag,ip + "<---ip from shared");

    }

    @Override
    public void onClick(View v) {
         final Intent intent = new Intent(this,ChooseAccount.class);

    if(accountype.getSelectedItem().equals("Pension")){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Pension account can only deposit money, and can only be withdrawn at the age of 77. \n press yes to create the account or press no to cancel");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Saveaccount saveaccount =  new Saveaccount();
                        saveaccount.execute();

                        try {
                            if(saveaccount.execute().get().equals(200)){
                                
                            }

                            startActivity(intent);
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }



    }
    public class Saveaccount extends AsyncTask<String, String, String> {

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
            String webapiadress = "http://" +ip +":8888/newAccount?Email="+Email+"&Accountname="+accountname+"&AccountType=" + accountype.getSelectedItem().toString();
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

            return reponse;
        }

    }


}