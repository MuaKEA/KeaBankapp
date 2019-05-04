package com.example.keabank;


import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.keabank.Logic.ServerGetRequest;
import com.example.keabank.Logic.Usefulmethods;
import com.example.keabank.Model.Accounts;
import com.example.keabank.internetConnetivity.ServerPostCall;
import java.util.ArrayList;


public class TransferMoneyToAccount extends AppCompatActivity implements View.OnClickListener {
    Spinner FromAcc, ToAcc;
    Button SendMoney;
    EditText amount;
    String Email;
    ArrayList<String> ArrayListoSpinner;
    ArrayList<Accounts> accountobjects;
    String Tag = "TransferMoneyToAccount";
    CheckBox checkBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_money_to_account);
        startup();
        Getvaluesfromsharedpref();
        getAllAccountNames();
        setupSpinner();
        SendMoney.setOnClickListener(this);

        }





    private void setupSpinner() {
        FromAcc = (Spinner) findViewById(R.id.FromAccount);
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, ArrayListoSpinner);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        FromAcc.setAdapter(dataAdapter1);

        ToAcc = (Spinner) findViewById(R.id.ToAccount);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, ArrayListoSpinner);
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

    public void getAllAccountNames(){
    ServerGetRequest serverGetRequest = new ServerGetRequest("/getaccounts?Email=" +Email);
        accountobjects= serverGetRequest.GetAllAccounobjects();
        ArrayListoSpinner= new ArrayList<>();
        for (int i = 0; i <accountobjects.size() ; i++) {
            ArrayListoSpinner.add(accountobjects.get(i).getAccountName() +" (" +accountobjects.get(i).getAccountType() +  ")\navailable:" + accountobjects.get(i).getCurrentDeposit());
        }

    }




    @Override
    public void onClick(View v) {


        switch (v.getId()) {



            case R.id.SendMoney:

                if (accountobjects.get(FromAcc.getSelectedItemPosition()).getAccountType().equals("Pension") &&  requirementschecker() && checkdeposit(accountobjects.get(FromAcc.getSelectedItemPosition()).getCurrentDeposit())) {
                    Log.d(Tag,"pension");
                    MakeTransActionHappen(FromAcc.getSelectedItemPosition(),ToAcc.getSelectedItemPosition());
                    Intent intent = new Intent(this,Menu.class);
                    startActivity(intent);

                } else if (!accountobjects.get(FromAcc.getSelectedItemPosition()).getAccountType().equals("Pension") && checkdeposit(accountobjects.get(FromAcc.getSelectedItemPosition()).getCurrentDeposit())){
                    Log.d(Tag,"ikke pension");

                    MakeTransActionHappen(FromAcc.getSelectedItemPosition(),ToAcc.getSelectedItemPosition());
                    Intent intent =  new Intent(this,Menu.class);
                   startActivity(intent);

                }

                break;


        }


    }

        private void MakeTransActionHappen(int positionFromAccount,int positionToAccount) {
            ServerPostCall serverPostCall = new ServerPostCall("/sendmoneyToOtherAccount?Email="+Email + "&TranceActionName=From Account" + accountobjects.get(positionFromAccount).getAccountName()+"&fromAccount="+accountobjects.get(positionFromAccount).getAccountName()+"&ToAccount="+accountobjects.get(positionToAccount).getAccountName()
                    +"&value="+amount.getText().toString()+"&sendingorReciving=true");

            serverPostCall.execute();

        }




        public boolean requirementschecker(){
            ServerGetRequest serverGetCall= new ServerGetRequest("/getCpr?Email="+Email);

            Log.d(Tag,serverGetCall.getCpr());



                Usefulmethods usefulmethods = new Usefulmethods(serverGetCall.getCpr());

                if(usefulmethods.ageChecker()){
                    Log.d(Tag,usefulmethods.ageChecker() +"check age");
                    return false;

                }else {
                     Toast.makeText(this,"you must be 77 or more to withdraw money",Toast.LENGTH_LONG).show();


                }

        return false;
        }

        public boolean checkdeposit(Double amounts) {
        if (amounts >= Double.valueOf(amount.getText().toString())) {

                return true;
            }
            amount.setError("Not enough money");
            Toast.makeText(this, "Not enough money please choose another amount and press send",
                    Toast.LENGTH_LONG).show();
            return false;

        }
}











