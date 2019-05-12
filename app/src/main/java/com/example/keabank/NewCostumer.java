package com.example.keabank;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.keabank.Logic.ServerPostRequest;

public class NewCostumer extends AppCompatActivity implements View.OnClickListener {
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

            ServerPostRequest requestConformationCode= new ServerPostRequest("/sendValidationCode?Email="+editTextsarray[0].getText().toString());
            requestConformationCode.execute();


            final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            LayoutInflater inflater = getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.conform_email_adress, null);
            dialogBuilder.setView(dialogView);
            final EditText conformEmailcode = (EditText) dialogView.findViewById(R.id.conformEmail);
            final Button createuserbtn = (Button) dialogView.findViewById(R.id.Conform_email_btn);
            final AlertDialog dialog = dialogBuilder.create();
            final Button backbtn= dialogView.findViewById(R.id.btn_back);


            backbtn.setOnClickListener(v2 -> {

                dialog.dismiss();

            });


            createuserbtn.setOnClickListener(v1 -> {
                if (TextUtils.isEmpty(conformEmailcode.getText().toString())) {
                    Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
                }


                    ServerPostRequest serverCall = new ServerPostRequest("/createuser?fullname="+editTextsarray[1].getText().toString()+"&username="+editTextsarray[0].getText().toString() + "&Cpr=" +editTextsarray[5].getText().toString()  +"&password="+editTextsarray[2].getText().toString() + "&conformationscode=" +conformEmailcode.getText().toString() );

                    if(serverCall.execute()==200){
                        LoginActivity.putExtra("usernmame", editTextsarray[0].getText().toString());
                        LoginActivity.putExtra("password", editTextsarray[2].getText().toString());
                        startActivity(LoginActivity);
                    }else {

                        Toast.makeText(this, "Wrong Conformation code", Toast.LENGTH_LONG).show();
                    }


            });
            dialog.show();


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
        }



        return isallok;
    }

    public boolean emptyfieldschecker() {
        boolean check = false;

        for (int i = 0; i < editTextsarray.length; i++) {

            if (editTextsarray[i].getText().length() <= 0) {
                editTextsarray[i].setError("Required");
                check=true;

            }
        }

        Log.d(Tag,"emptyfieldschecker-->"+check);

        return check;
    }







}



