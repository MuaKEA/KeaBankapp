package com.example.keabank;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class Login extends AppCompatActivity implements View.OnClickListener  {
    EditText Email, Password;
    CheckBox remember_Checkbox;
    Button Login;
    String Tag = "Login class-->";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getData();
        startup();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Email.setText(extras.getString("username"));
            Password.setText(extras.getString("password"));
        }

        Login.setOnClickListener(this);
    }

    private void startup() {
        Email = findViewById(R.id.email);
        Password = findViewById(R.id.password);
        remember_Checkbox = findViewById(R.id.Remember_me);
        Login = findViewById(R.id.Sign_in);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.Sign_in:

                Log.d(Tag, "sign in button");
                Passwordchecker passwordchecker = new Passwordchecker();
                try {
                    loginchecker(passwordchecker.execute().get());


                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                break;



        }
    }

    public void loginchecker (String serverresponse ){
        Intent intent = new Intent(this, Menu.class);
        Log.d(Tag,serverresponse);


        if(remember_Checkbox.isChecked() && serverresponse.equals(String.valueOf(200))){
            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("username", Email.getText().toString());
            editor.putString("password",Password.getText().toString());
            editor.apply();
            startActivity(intent);

        }else if(serverresponse.equals(String.valueOf(200))){
            startActivity(intent);


        }else
            Toast.makeText(this, "Wrong password",
                    Toast.LENGTH_LONG).show();
    }

    public void getData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        String username = pref.getString("username","");
        String password = pref.getString("password","");
        Log.d(Tag,username);
        Log.d(Tag,password);


        if(!username.equalsIgnoreCase("") || !password.equalsIgnoreCase("")){

            Intent intent = new Intent(this,Menu.class);
            startActivity(intent);

        }





    }




    public class Passwordchecker extends AsyncTask<String, String, String> {

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

            String webapiadress = "http://10.149.88.167:8888/loginvalidation?" +"username=" + Email.getText().toString()+ "&password=" + Password.getText().toString();
            String reponse="";
            URL url;
            try {
                url = new URL(webapiadress);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.connect();
                reponse=String.valueOf(con.getResponseCode());
                Log.d("Server response",reponse);

            } catch (Exception e) {
                e.printStackTrace();


            }

            return reponse;
        }


    }
}