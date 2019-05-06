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

import com.example.keabank.Logic.ServerGetRequest;
import com.example.keabank.Logic.ServerPostRequest;
import com.example.keabank.Model.Accounts;

import java.util.ArrayList;

public class SendMoneyToOthers extends AppCompatActivity implements View.OnClickListener {
Spinner accountSpinner;
    private ArrayList<String> ArrayListoSpinner;
    private ArrayList<Accounts> accountobjects;
    private String Email;
    private String Tag="SendMoneyToOthers";
    Spinner fromaccoutspinner;
    EditText registrationNumber,AccNumber,edittextAmount;
    Button sendmoney;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_money_to_others);
        Getvaluesfromsharedpref();
        startup();
        getAllAccountNames();
        setupSpinner();


  sendmoney.setOnClickListener(this);

    }

    private void startup() {
    fromaccoutspinner=findViewById(R.id.accSpinner);
    registrationNumber=findViewById(R.id.accoutReg);
    edittextAmount=findViewById(R.id.edittextAmount);
    AccNumber=findViewById(R.id.AccNumber);
    sendmoney=findViewById(R.id.sendmoney);
    }


    private void setupSpinner() {
        accountSpinner = (Spinner) findViewById(R.id.accSpinner);
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, ArrayListoSpinner);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accountSpinner.setAdapter(dataAdapter1);

    }
    public void getAllAccountNames(){
        ServerGetRequest serverGetRequest = new ServerGetRequest("/getaccounts?Email=" +Email);
        accountobjects= serverGetRequest.GetAllAccounobjects();
        ArrayListoSpinner= new ArrayList<>();
        for (int i = 0; i <accountobjects.size() ; i++) {
            ArrayListoSpinner.add(accountobjects.get(i).getAccountName() +" (" +accountobjects.get(i).getAccountType() +  ")\navailable:" + accountobjects.get(i).getCurrentDeposit());
        }

    }

    private void Getvaluesfromsharedpref() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        Email = pref.getString("username", "");
        Log.d(Tag, Email + "<---Email from shared");

    }

    @Override
    public void onClick(View v) {

            if(isfieldsvaild()){
//name = "Email") String email, @RequestParam(name = "Fromaccout") String Fromaccout,@RequestParam(name = "Fromatype") String Fromatype, @RequestParam(name = "reg") Long reg,@RequestParam(name = "accountnb") Long accountnb,@RequestParam(name = "ammount") Long ammount
                ServerPostRequest serverPostRequest =new ServerPostRequest("/sendtootherusers?Email="+ Email+"&Fromaccout=" + accountobjects.get(accountSpinner.getSelectedItemPosition()).getAccountName() +"&Fromatype="
                        + accountobjects.get(accountSpinner.getSelectedItemPosition()).getAccountType() +"&reg=" + registrationNumber.getText().toString() + "&accountnb=" + AccNumber.getText().toString() +"&ammount=" + edittextAmount.getText().toString() );


                if(serverPostRequest.getReponse()==200){
                    Intent intent =  new Intent(this,Menu.class);
                    startActivity(intent);

                }else
                    Toast.makeText(this,"something went wrong try again later",Toast.LENGTH_SHORT).show();



            }




    }

    private boolean isfieldsvaild() {

        if(registrationNumber.getText().toString().equals("") && edittextAmount.getText().toString().equals("") && AccNumber.getText().toString().equals("")){

            Toast.makeText(this,"no empty fields",Toast.LENGTH_LONG).show();
            registrationNumber.setError("needed");
            edittextAmount.setError("needed");
            registrationNumber.setError("needed");
            return false;
        }

            return true;

    }
}
