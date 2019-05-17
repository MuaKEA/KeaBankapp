package com.example.keabank;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.keabank.Logic.ServerPostRequest;
import com.example.keabank.Logic.TransactionsManager;

import static com.example.keabank.Logic.TransactionsManager.startTransactions;


public class Login extends AppCompatActivity implements View.OnClickListener {
    EditText Email, Password;
    CheckBox remember_Checkbox;
    Button Login, Createuser,forgotpasswordbtn;
    String Tag = "Login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        startup();
        getData();
        starttransactions();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Email.setText(extras.getString("usernmame"));
        }

        Login.setOnClickListener(this);
        Createuser.setOnClickListener(this);
        forgotpasswordbtn.setOnClickListener(this);

    }

    private void starttransactions() {
        startTransactions(this);
    }

    private void startup() {
        Email = findViewById(R.id.conformEmail);
        Createuser=findViewById(R.id.RegisterUser);
        Password = findViewById(R.id.password);
        remember_Checkbox = findViewById(R.id.Remember_me);
        Login = findViewById(R.id.Sign_in);
        forgotpasswordbtn= findViewById(R.id.forgotpassword);

        Password.setText("12345");
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

                break;
            case R.id.forgotpassword:


                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);




                LayoutInflater inflater = getLayoutInflater();

                final View dialogView = inflater.inflate(R.layout.forgot_password, null);
                dialogBuilder.setView(dialogView);

                final EditText editEmail = (EditText) dialogView.findViewById(R.id.conformEmail);
                final Button btnReset = (Button) dialogView.findViewById(R.id.Conform_email_btn);
                final ProgressBar progressBar1 = (ProgressBar) dialogView.findViewById(R.id.progressBar);
                final AlertDialog dialog = dialogBuilder.create();
                final Button backbtn= dialogView.findViewById(R.id.btn_back);


                backbtn.setOnClickListener(v2 -> {

                    dialog.dismiss();




                });


                btnReset.setOnClickListener(v1 -> {
                    if (TextUtils.isEmpty(editEmail.getText().toString())) {
                        Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
                    }

                    ServerPostRequest Emailchecker=  new ServerPostRequest("/checkemail?Email=" + editEmail.getText().toString());

                    if (Emailchecker.getReponse()==200){
                        ServerPostRequest RequestnewPassword= new ServerPostRequest("/forgotpassword?Email=" + editEmail.getText().toString());
                           Log.d(Tag,RequestnewPassword.getReponse() + "<--RequestnewPassword");
                        dialog.dismiss();
                    }




                });
                dialog.show();

                break;


        }


        }






    public void loginchecker (Integer serverresponse){
        Intent intent = new Intent(this, Menu.class);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();


        Log.d(Tag,serverresponse.toString());




      if(serverresponse==200){
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
        serverPostRequest.execute();
        return serverPostRequest.getReponse();
}




}