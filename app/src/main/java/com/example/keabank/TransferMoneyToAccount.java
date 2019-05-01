package com.example.keabank;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.keabank.Model.Accounts;
import com.example.keabank.internetConnetivity.ServerGetCall;
import com.example.keabank.internetConnetivity.ServerPostCall;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class TransferMoneyToAccount extends AppCompatActivity implements View.OnClickListener {
    Spinner FromAcc, ToAcc;
    Button SendMoney;
    EditText amount;
    String Email;
    ArrayList<String> accountnames;
    ArrayList<Accounts> accounts;
    String Tag = "TransferMoneyToAccount";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_money_to_account);
        startup();
        Getvaluesfromsharedpref();
        try {
            getAllAccountNames();
        } catch (Exception e) {
            e.printStackTrace();
        }

        setupSpinner();
        SendMoney.setOnClickListener(this);


    }

    private void setupSpinner() {
        FromAcc = (Spinner) findViewById(R.id.FromAccount);
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, accountnames);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        FromAcc.setAdapter(dataAdapter1);

        ToAcc = (Spinner) findViewById(R.id.ToAccount);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, accountnames);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ToAcc.setAdapter(dataAdapter2);

    }


    public void startup() {
        FromAcc = findViewById(R.id.FromAccount);
        ToAcc = findViewById(R.id.ToAccount);
        SendMoney = findViewById(R.id.SendMoney);
        amount = findViewById(R.id.amount);
    }

    private void Getvaluesfromsharedpref() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        Email = pref.getString("username", "");
        Log.d(Tag, Email + "<---Email from shared");

    }

    public void getAllAccountNames() throws ExecutionException, InterruptedException {
        accountnames = new ArrayList<>();
        ServerGetCall serverGetCall = new ServerGetCall("/getaccounts?Email=" + Email, "getAllAccountsAndDeposit");
        accounts = serverGetCall.execute().get();

        for (int i = 0; i < accounts.size(); i++) {
            accountnames.add(accounts.get(i).getAccountName() + " " + accounts.get(i).getAccountType() + "\navailable deposit " + accounts.get(i).getCurrentDeposit());
        }

    }


    @Override
    public void onClick(View v) {
        Log.d(Tag,accounts.get(FromAcc.getSelectedItemPosition()).getCurrentDeposit()+" ");
        Log.d(Tag,Double.parseDouble(amount.getText().toString()) +" ");
        if (accounts.get(FromAcc.getSelectedItemPosition()).getCurrentDeposit() > Double.parseDouble(amount.getText().toString())) {

            MakeTransActionHappen(FromAcc.getSelectedItemPosition(),ToAcc.getSelectedItemPosition());
            Intent intent = new Intent(this,Menu.class);
            startActivity(intent);

        } else {
            amount.setError("Not enough money");
            Toast.makeText(this, "Not enough money please choose another amount and press send",
                    Toast.LENGTH_LONG).show();

        }


    }

    private void MakeTransActionHappen(int positionFromAccount,int positionToAccount) {
        ServerPostCall serverPostCall = new ServerPostCall("/sendmoneyToOtherAccount?Email="+Email + "&TranceActionName=From Account" + accounts.get(positionFromAccount).getAccountName()+"&fromAccount="+accounts.get(positionFromAccount).getAccountName()+"&ToAccount="+accounts.get(positionToAccount).getAccountName()
                +"&value="+amount.getText().toString()+"&sendingorReciving=true");
        serverPostCall.execute();

    }
}
