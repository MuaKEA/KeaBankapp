package com.example.keabank;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;

import static com.example.keabank.Logic.TransactionsManager.startTransactions;

public class TransferMoneyMenu extends AppCompatActivity implements View.OnClickListener {
String Tag ="TransferMoneyMenu";
String Email;
    CardView TransferOwnAccounts,paybills,TransferToOthers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_money_menu);
        startup();

        TransferOwnAccounts.setOnClickListener(this);
        paybills.setOnClickListener(this);
        TransferToOthers.setOnClickListener(this);
    }

    private void startup() {
        TransferOwnAccounts=findViewById(R.id.TransferOwnAccounts);
        paybills=findViewById(R.id.paybills);
        TransferToOthers=findViewById(R.id.TransferMoneyToOthers);
        startTransactions(this);
        Getvaluesfromsharedpref();
    }

    private void Getvaluesfromsharedpref() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        Email=pref.getString("username","");
        Log.d(Tag,Email + "<---Email from shared");

    }

    @Override
    public void onClick(View v) {
      Intent intent;
        switch (v.getId()){

            case R.id.TransferOwnAccounts:
               intent = new Intent(this,TransferMoneyToAccount.class);
               startActivity(intent);
                break;

            case R.id.paybills:
                intent = new Intent(this,Paybills.class);
                startActivity(intent);
                break;

            case R.id.TransferMoneyToOthers:
                intent = new Intent(this,SendMoneyToOthers.class);
                startActivity(intent);
                break;

        }
        
    }
}
