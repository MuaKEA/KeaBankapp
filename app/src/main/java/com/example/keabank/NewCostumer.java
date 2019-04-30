package com.example.keabank;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.keabank.internetConnetivity.ServerPostCall;
import java.util.concurrent.ExecutionException;

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
            try {
                Saveuser();
                LoginActivity.putExtra("usernmame", editTextsarray[0].getText().toString());
                LoginActivity.putExtra("password", editTextsarray[2].getText().toString());
                startActivity(LoginActivity);

            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


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


        Log.d(Tag,"condition-->" +isallok);

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


   public boolean Saveuser() throws ExecutionException, InterruptedException {

       ServerPostCall serverCall = new ServerPostCall("/createuser?fullname="+editTextsarray[1].getText().toString()+"&username="+editTextsarray[0].getText().toString() + "&Cpr=" +editTextsarray[5].getText().toString()  +"&password="+editTextsarray[2].getText().toString());
       return serverCall.execute().get().equals("200");
   }

}



