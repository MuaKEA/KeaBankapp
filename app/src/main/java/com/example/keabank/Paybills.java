package com.example.keabank;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.keabank.Logic.ServerReponse;
import com.example.keabank.Logic.Usefulmethods;
import com.example.keabank.Model.Accounts;
import com.example.keabank.internetConnetivity.ServerGetCall;
import com.example.keabank.internetConnetivity.ServerPostCall;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Paybills extends AppCompatActivity implements View.OnClickListener {
    Spinner FromAccount, servicecode;
    EditText reg, accountnumber, TransactionName,ammout,date;
    Button Sendbutton;
    ArrayList<String> accountnames;
    ArrayList<Accounts> accountObjecs;
    String Email;
    String Tag="Paybills";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paybills);
        startup();
        Getvaluesfromsharedpref();
        getAllAccounts();
        setupSpinner();


        Sendbutton.setOnClickListener(this);
        date.setOnClickListener(this);

    }


    private void startup() {

        FromAccount = findViewById(R.id.FromAccount);
        servicecode = findViewById(R.id.servicecode);
        reg = findViewById(R.id.registrationnum);
        Sendbutton = findViewById(R.id.Sendbutton);
        TransactionName = findViewById(R.id.transactionname);
        accountnumber=findViewById(R.id.accountnumb);
        ammout=findViewById(R.id.billcost);
        date=findViewById(R.id.date);



    }

    private void setupSpinner() {
        FromAccount = (Spinner) findViewById(R.id.FromAccount);
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, accountnames);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        FromAccount.setAdapter(dataAdapter1);

        }
    public void getAllAccounts() {
        accountnames = new ArrayList<>();
        ServerReponse getAllAccounts = new ServerReponse("/getaccounts?Email=" + Email, "getAllAccountsAndDeposit");
        accountObjecs = getAllAccounts.GetAllAccounobjects();

        for (int i = 0; i < accountObjecs.size(); i++) {
            accountnames.add(accountObjecs.get(i).getAccountName() + " (" + accountObjecs.get(i).getAccountType() + ")  Available:" + accountObjecs.get(i).getCurrentDeposit());

        }
    }


    private void Getvaluesfromsharedpref() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        Email=pref.getString("username","");
        Log.d(Tag,Email + "<---Email from shared");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.Sendbutton:

                if (checkifAccountExist()) {

                    ServerReponse serverReponse = new ServerReponse("/paybill?Email=" + Email + "&TranceActionName=" + TransactionName.getText().toString() + "&fromAccount=" + accountObjecs.get(FromAccount.getSelectedItemPosition()).getAccountName() + "&value=" + ammout.getText().toString() + "&date=" + date.getText().toString() + "&sendingorReciving=" + true, " ");

                    if (serverReponse.GetReponseCode() == 200) {
                        Intent Approved = new Intent(this, TransferMoneyMenu.class);
                        startActivity(Approved);
                    } else
                        Toast.makeText(this, "something went Wrong", Toast.LENGTH_LONG).show();
                }

                        break;

            case R.id.date:
                Usefulmethods calender= new Usefulmethods(date,this);
                calender.CalenderEdittext();
                break;

        }
    }

    private boolean checkifAccountExist() {

        ServerReponse checkBill = new ServerReponse("/checkbillsexist?digits=" + servicecode.getSelectedItem().toString().substring(1) + "&accountnumber=" + accountnumber.getText().toString()
                + "&registrationNumber=" + reg.getText().toString(), "statuschecjer");
        int reponsCode=checkBill.GetReponseCode();


        if (reponsCode > 0) {
            Log.d(Tag,reponsCode + " ");
            return true;
        }

        return false;
    }
}