package com.example.keabank;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.keabank.Model.Accounts;
import com.example.keabank.internetConnetivity.ServerGetCall;


import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Login extends AppCompatActivity implements View.OnClickListener  {
    EditText Email, Password;
    CheckBox remember_Checkbox;
    Button Login, Createuser;
    String Tag = "Login class";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        startup();
        getData();


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Email.setText(extras.getString("usernmame"));
        }

        Login.setOnClickListener(this);
        Createuser.setOnClickListener(this);

    }

    private void startup() {
        Email = findViewById(R.id.email);
        Createuser=findViewById(R.id.RegisterUser);
        Password = findViewById(R.id.password);
        remember_Checkbox = findViewById(R.id.Remember_me);
        Login = findViewById(R.id.Sign_in);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.Sign_in:

                Log.d(Tag, "sign in button");
                try {
                   loginchecker(UsernameAndPasswordvalidation());

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                break;

            case R.id.RegisterUser:
                Intent intent = new Intent(this,NewCostumer.class);
                startActivity(intent);

        }
    }

    public void loginchecker (Integer serverresponse){
        Intent intent = new Intent(this, Menu.class);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();


        Log.d(Tag,serverresponse.toString());




      if(serverresponse.equals(200)){
            editor.putString("username", Email.getText().toString().trim());
            editor.putBoolean("checkbox",remember_Checkbox.isChecked());
            editor.apply();
            startActivity(intent);


        }else
            Toast.makeText(this, "Wrong password",
                    Toast.LENGTH_LONG).show();
    }

    public void getData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        String username = pref.getString("username","");
        boolean ischeckedboxchecked=pref.getBoolean("checkbox",false);

        if (ischeckedboxchecked && (username != null)) {
            Log.d(Tag, username + "<-- username after if");
            Email.setText(username);
            remember_Checkbox.setChecked(true);

        }
    }



public Integer UsernameAndPasswordvalidation() throws ExecutionException, InterruptedException {


    ServerGetCall serverGetCall = new ServerGetCall("/loginvalidation?" +"username=" + Email.getText().toString()+ "&password=" + Password.getText().toString(),"ResponseCode");
    ArrayList<Accounts> respons=serverGetCall.execute().get();


        return respons.get(0).getResonseCode();
}


}