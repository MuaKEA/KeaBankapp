package com.example.keabank;

import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class TransferMoneyToAccount extends AppCompatActivity {
    Spinner FromAcc;
    Spinner ToAcc;
    Button S;
    TextView Mes;
    String Tag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_money_to_account);
        FromAcc = findViewById(R.id.FromAccount);
        ToAcc = findViewById(R.id.ToAccount);
        S = findViewById(R.id.Confirm);
        Mes = findViewById(R.id.Message);

        Log.d("FromAcc", FromAcc.getSelectedItemId() + " ");
        if(savedInstanceState != null){
            Mes.setText(savedInstanceState.getString("Confirmation"));

        }


    }

    public void getAccount(){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        String accountName = pref.getString("username","");

            if(accountName != null){
                Log.d(Tag, accountName);
                editor.
            }
    }


}
