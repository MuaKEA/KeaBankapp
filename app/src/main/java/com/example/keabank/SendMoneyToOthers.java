package com.example.keabank;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.keabank.Logic.ServerGetRequest;
import com.example.keabank.Logic.ServerPostRequest;
import com.example.keabank.Logic.Usefulmethods;
import com.example.keabank.Model.Accounts;
import com.example.keabank.Model.Transactions;

import java.util.ArrayList;
import java.util.Objects;

import static com.example.keabank.Logic.SavingAndReadingFiles.saveToFile;
import static com.example.keabank.Logic.TransactionsManager.startTransactions;

public class SendMoneyToOthers extends AppCompatActivity implements View.OnClickListener {
Spinner accountSpinner;
    private ArrayList<String> ArrayListoSpinner;
    private ArrayList<Accounts> accountobjects;
    private String Email;
    private String Tag="SendMoneyToOthers";
    Spinner fromaccoutspinner;
    EditText registrationNumber,AccNumber,edittextAmount,txtToreciever,date,transactionName;
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
  date.setOnClickListener(this);

    }

    private void startup() {
    fromaccoutspinner=findViewById(R.id.accSpinner);
    registrationNumber=findViewById(R.id.accoutReg);
    edittextAmount=findViewById(R.id.edittextAmount);
    AccNumber=findViewById(R.id.AccNumber);
    sendmoney=findViewById(R.id.sendmoney);
    txtToreciever=findViewById(R.id.txtToreciever);
    transactionName =findViewById(R.id.transactionName);
    date=findViewById(R.id.edittext_date);


    }


    private void setupSpinner() {
        accountSpinner = (Spinner) findViewById(R.id.accSpinner);
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, ArrayListoSpinner);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accountSpinner.setAdapter(dataAdapter1);

    }
    public void getAllAccountNames(){
        ServerGetRequest serverGetRequest = new ServerGetRequest(Email);
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


        switch (v.getId()){


            case R.id.sendmoney:
            if(isfieldsvaild() && checkdeposit() && checkifAccountexist()){
                ServerPostRequest sendconformationcode = new ServerPostRequest("/sendserviceCode?Email="+Email);
                sendconformationcode.execute();

                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
                LayoutInflater inflater = getLayoutInflater();




                final View dialogView = inflater.inflate(R.layout.nemidvalidation_fragment, null);

                final TextView fromAccount= dialogView.findViewById(R.id.fromAccount);
                final TextView accountType= dialogView.findViewById(R.id.accountType);
                final TextView accountNumber= dialogView.findViewById(R.id.accountNumber);
                final TextView registrationnumber= dialogView.findViewById(R.id.registrationnumber);
                final TextView txtToreceiver= dialogView.findViewById(R.id.txtToreceiver);
                final TextView amount= dialogView.findViewById(R.id.amount);
                final TextView exitdialog= dialogView.findViewById(R.id.exit_btn);
                final EditText conformationCode= dialogView.findViewById(R.id.conformationCode);
                final Button SendMoneyBtn= dialogView.findViewById(R.id.SendMoneyBtn);
                dialogBuilder.setView(dialogView);

                fromAccount.setText(accountobjects.get(fromaccoutspinner.getSelectedItemPosition()).getAccountName());
                accountType.setText(accountobjects.get(fromaccoutspinner.getSelectedItemPosition()).getAccountType());
                accountNumber.setText(AccNumber.getText().toString());
                registrationnumber.setText(registrationNumber.getText().toString());
                txtToreceiver.setText(txtToreciever.getText().toString());
                amount.setText(edittextAmount.getText().toString());


                 AlertDialog dialog = dialogBuilder.create();
                Objects.requireNonNull(dialog.getWindow()).setLayout(600, 800);


                exitdialog.setOnClickListener(v1 -> dialog.dismiss());



                if(TextUtils.isEmpty(conformationCode.getText().toString())){
                    conformationCode.setError("Required");
                    Toast.makeText(this,"Error",Toast.LENGTH_LONG).show();

                }

                SendMoneyBtn.setOnClickListener(v2 -> {

                    //ServerPostRequest serverPostRequest = new ServerPostRequest("/accountchecker?reg="+ registrationNumber.getText().toString()+"&accountnumber=" + AccNumber.getText().toString());
                      ServerPostRequest checkservicescode= new ServerPostRequest("/ServiceCodechecker?Email=" + Email + "&servicecode=" + conformationCode.getText().toString());
                      checkservicescode.execute();
                      if(checkservicescode.getReponse()==200){

                            Transactions transactionpbs =new Transactions(transactionName.getText().toString(),txtToreciever.getText().toString(),accountobjects.get(fromaccoutspinner.getSelectedItemPosition()).getRegistrationnumber(),accountobjects.get(fromaccoutspinner.getSelectedItemPosition()).getAccountNumber(),Long.valueOf(registrationNumber.getText().toString()),Long.valueOf(AccNumber.getText().toString()) ,date.getText().toString(),amount.getText().toString());
                            saveToFile(this,transactionpbs);

                            startTransactions(this);
                            Intent transationsactivity = new Intent(this,TransferMoneyMenu.class);
                            startActivity(transationsactivity);

                        }else{
                            Toast.makeText(this,"Error: Wrong conformations code" , Toast.LENGTH_LONG).show();
                            conformationCode.setError("Wrong Code");


                        }



                });
                dialog.show();


                }else
                    Toast.makeText(this,"something went wrong try again later",Toast.LENGTH_SHORT).show();

                break;

            case R.id.edittext_date:
                Usefulmethods calender = new Usefulmethods(date, this);
                calender.CalenderEdittext();
                break;

        }
    }

    private boolean checkifAccountexist() {
    ServerPostRequest checkifaccountexist= new ServerPostRequest("/accountchecker?reg=" +registrationNumber.getText().toString() +"&accountnumber=" + AccNumber.getText().toString());
    checkifaccountexist.execute();

    if (checkifaccountexist.getReponse()==200){
        return true;
    }
    Toast.makeText(this,"Error: Account doesnt exist please check account and registration number",Toast.LENGTH_LONG).show();
    registrationNumber.setError("check again");
    AccNumber.setError("check again");


    return false;

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

    public boolean checkdeposit() {
        if (accountobjects.get(accountSpinner.getSelectedItemPosition()).getCurrentDeposit() >= Double.valueOf(edittextAmount.getText().toString())) {

            return true;
        }
        edittextAmount.setError("Not enough money");
        Toast.makeText(this, "Not enough money please choose another amount and press send",
                Toast.LENGTH_LONG).show();
        return false;

    }

}
