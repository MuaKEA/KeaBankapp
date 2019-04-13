package com.example.keabank;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity implements View.OnClickListener {
    String Tag = "Menu Class";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);






}

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.balance:
                Intent bal = new Intent(this, ChooseAccount.class);
                startActivity(bal);
                break;

            case R.id.transfer:
                Intent trans = new Intent(this, TransferAccount.class);
                startActivity(trans);
                break;

            case R.id.account:
                Intent acc = new Intent(this, Account.class);
                startActivity(acc);
                break;

            case R.id.currency:
                Intent curr = new Intent(this, Currency.class);
                startActivity(curr);
                break;
        }
    }
    }
