package com.example.keabank;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;

public class Menu extends AppCompatActivity implements View.OnClickListener {
    String Tag = "Menu Class";
    CardView myaccount,Accounts,currentcy,TranferMoney;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


init();
currentcy.setOnClickListener(this);
Accounts.setOnClickListener(this);
myaccount.setOnClickListener(this);
TranferMoney.setOnClickListener(this);


}

    private void init() {
        myaccount=findViewById(R.id.myaccount);
        Accounts=findViewById(R.id.accounts);
        currentcy=findViewById(R.id.currency);
        TranferMoney=findViewById(R.id.moneytranfer);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.transfer:
                Intent trans = new Intent(this, SeeTransActions.class);
                startActivity(trans);
                break;

            case R.id.accounts:
                Intent acc = new Intent(this,ChooseAccount.class);
                startActivity(acc);
                break;

            case R.id.currency:
                Intent curr = new Intent(this, CreateAccount.class);
                startActivity(curr);
                break;
            case R.id.moneytranfer:
                Intent moneyT = new Intent(this, Currency.class);
                startActivity(moneyT);
                break;

        }
    }
    }
