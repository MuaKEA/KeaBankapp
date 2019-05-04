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

import com.example.keabank.Logic.ServerGetRequest;
import com.example.keabank.Logic.ServerPostRequest;

public class Login extends AppCompatActivity implements View.OnClickListener  {
    EditText Email, Password;
    CheckBox remember_Checkbox;
    Button Login, Createuser;
    String Tag = "Login";

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


                   loginchecker(UsernameAndPasswordvalidation());



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
          Log.d(Tag,"setting shared pref");
          editor.putString("username", Email.getText().toString().trim());
            editor.putBoolean("checkbox",remember_Checkbox.isChecked());
            editor.apply();
            startActivity(intent);


        }else
            Toast.makeText(this, "username or password is wrong or both who knows",
                    Toast.LENGTH_LONG).show();
    }

    public void getData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        String username = pref.getString("username",null);
        boolean ischeckedboxchecked=pref.getBoolean("checkbox",false);

        if (username!=null && ischeckedboxchecked) {
            Log.d(Tag, username + "<-- username after if");
            Email.setText(username);
            remember_Checkbox.setChecked(true);

        }
    }



public Integer UsernameAndPasswordvalidation()  {


    ServerPostRequest serverPostRequest = new ServerPostRequest("/loginvalidation?" +"username=" + Email.getText().toString()+ "&password=" + Password.getText().toString());

    Log.d(Tag,serverPostRequest.getReponse() +"<--Server Response");


    return serverPostRequest.getReponse();
}


}