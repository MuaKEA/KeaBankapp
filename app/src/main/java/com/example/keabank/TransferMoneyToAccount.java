package com.example.keabank;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.keabank.Logic.ServerPostRequest;
import com.example.keabank.Logic.Usefulmethods;
import com.example.keabank.Model.Accounts;
import com.example.keabank.Model.Transactions;
import com.example.keabank.Logic.ServerGetRequest;

import java.time.LocalDate;
import java.util.ArrayList;

import static com.example.keabank.Logic.SavingAndReadingFiles.saveToFile;


public class TransferMoneyToAccount extends AppCompatActivity implements View.OnClickListener {
    Spinner FromAcc, ToAcc;
    Button SendMoney;
    EditText amounttosend;
    String Email;
    ArrayList<String> ArrayListoSpinner;
    ArrayList<Accounts> accountobjects;
    String TAG = "TransferMoneyToAccount";
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
        FromAcc = findViewById(R.id.AmountTosend);
        ToAcc = findViewById(R.id.ToAccount);
        SendMoney = findViewById(R.id.SendMoney);
        amounttosend = findViewById(R.id.amount);

    }

    private void Getvaluesfromsharedpref() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        Email = pref.getString("username", "");
        Log.d(TAG, Email + "<---Email from shared");

    }

    public void getAllAccountNames(){
    ServerGetRequest serverGetRequest = new ServerGetRequest(Email);
        accountobjects= serverGetRequest.GetAllAccounobjects();
        ArrayListoSpinner= new ArrayList<>();
        for (int i = 0; i <accountobjects.size() ; i++) {
            ArrayListoSpinner.add(accountobjects.get(i).getAccountName() +" (" +accountobjects.get(i).getAccountType() +  ")\navailable:" + accountobjects.get(i).getCurrentDeposit());
        }

    }

    @Override
    public void onClick(View v) {



            if (accountobjects.get(FromAcc.getSelectedItemPosition()).getAccountType().equals("Pension") && requirementschecker() && checkdeposit(accountobjects.get(FromAcc.getSelectedItemPosition()).getCurrentDeposit()) || accountobjects.get(ToAcc.getSelectedItemPosition()).getAccountType().equals("Pension") && checkdeposit(accountobjects.get(FromAcc.getSelectedItemPosition()).getCurrentDeposit())) {

                Log.d(TAG, "onClick: inside if statement pension");
                ServerPostRequest sendconformationcode = new ServerPostRequest("/sendserviceCode?Email=" + Email);
                sendconformationcode.execute();

                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.nemidvalidation_fragment, null);
                dialogView.findViewById(R.id.textmessagetoreciever).setVisibility(View.GONE);
                dialogView.findViewById(R.id.txtToreceiver).setVisibility(View.GONE);
                TextView newnameToAccount = dialogView.findViewById(R.id.toaccountNumber);
                TextView newnameToAacoountType = dialogView.findViewById(R.id.toregistrationnumer);
                newnameToAacoountType.setText(getString(R.string.account_type));
                newnameToAccount.setText(getString(R.string.to_account));

                final TextView fromAccount = dialogView.findViewById(R.id.fromAccount);
                final TextView accountType = dialogView.findViewById(R.id.accountType);
                final TextView accountNumber = dialogView.findViewById(R.id.accountNumber);
                final TextView registrationnumber = dialogView.findViewById(R.id.registrationnumber);
                final TextView amount = dialogView.findViewById(R.id.amount);
                final TextView exitdialog = dialogView.findViewById(R.id.exit_btn);
                final EditText conformationCode = dialogView.findViewById(R.id.conformationCode);
                final Button SendMoneyBtn = dialogView.findViewById(R.id.SendMoneyBtn);
                dialogBuilder.setView(dialogView);

                fromAccount.setText(accountobjects.get(FromAcc.getSelectedItemPosition()).getAccountName());
                accountType.setText(accountobjects.get(FromAcc.getSelectedItemPosition()).getAccountType());
                accountNumber.setText(accountobjects.get(ToAcc.getSelectedItemPosition()).getAccountName());
                registrationnumber.setText(accountobjects.get(ToAcc.getSelectedItemPosition()).getAccountType());

                amount.setText(amounttosend.getText().toString());
                AlertDialog dialog = dialogBuilder.create();


                exitdialog.setOnClickListener(v1 -> dialog.dismiss());


                SendMoneyBtn.setOnClickListener(v2 -> {

                    if (TextUtils.isEmpty(conformationCode.getText().toString())) {
                        conformationCode.setError("Required");
                        Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();

                    } else {

                        MakeTransActionHappen();


                    }

                });
                dialog.show();


            } else if (!accountobjects.get(ToAcc.getSelectedItemPosition()).getAccountType().equals("Pension") && !accountobjects.get(FromAcc.getSelectedItemPosition()).getAccountType().equals("Pension") && checkdeposit(accountobjects.get(FromAcc.getSelectedItemPosition()).getCurrentDeposit())) {


                MakeTransActionHappen();


            }


        }




        private void MakeTransActionHappen() {


                Transactions transaction = new Transactions("hallo", accountobjects.get(FromAcc.getSelectedItemPosition()).getRegistrationnumber(), accountobjects.get(FromAcc.getSelectedItemPosition()).getAccountNumber(), accountobjects.get(ToAcc.getSelectedItemPosition()).getRegistrationnumber(), accountobjects.get(ToAcc.getSelectedItemPosition()).getAccountNumber(), LocalDate.now().toString(), amounttosend.getText().toString());
                saveToFile(this, transaction);
                Intent intent = new Intent(this, Menu.class);
                startActivity(intent);
            }




        public boolean requirementschecker(){
            ServerGetRequest serverGetCall= new ServerGetRequest(Email);




                Usefulmethods usefulmethods = new Usefulmethods(serverGetCall.getCpr());

                if(usefulmethods.ageChecker()) {
                    Log.d(TAG, usefulmethods.ageChecker() + "check age");
                    return false;

                } else {
                    amounttosend.setError("you must be 77 or more to withdraw money");


                }

        return false;
        }

        public boolean checkdeposit(Double amounts) {
        if (amounts >= Double.valueOf(amounttosend.getText().toString())) {


                return true;
            }
            amounttosend.setError("Error:");
            Toast.makeText(this, "Not enough money or not accepted value",
                    Toast.LENGTH_LONG).show();
            return false;

        }





}











