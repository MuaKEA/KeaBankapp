package com.example.keabank;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import com.example.keabank.Logic.ServerGetRequest;
import com.example.keabank.Logic.ServerPostRequest;
import com.example.keabank.Logic.Usefulmethods;
import com.example.keabank.Model.Accounts;

import java.util.ArrayList;
import java.util.Objects;

public class Paybills extends AppCompatActivity implements View.OnClickListener {
    Spinner FromAccount, serialnumber;
    EditText reg, accountnumber, TransactionName, ammout, date,txtToReceiver;
    Button Sendbutton;
    CheckBox pbscheckbox;
    ArrayList<String> accountnames;
    ArrayList<Accounts> accountObjecs;
    String Email;
    String Tag = "Paybills";
    EditText[] editTextsArray;

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

        FromAccount = findViewById(R.id.AmountTosend);
        serialnumber = findViewById(R.id.serialnumber);
        reg = findViewById(R.id.registrationnum);
        Sendbutton = findViewById(R.id.Sendbutton);
        TransactionName = findViewById(R.id.TransactionName);
        accountnumber = findViewById(R.id.accountnumb);
        ammout = findViewById(R.id.billcost);
        date = findViewById(R.id.date);
        txtToReceiver=findViewById(R.id.txtToReceiver);
        pbscheckbox=findViewById(R.id.pbscheckbox);
        editTextsArray= new EditText[]{TransactionName, ammout, txtToReceiver, date, reg, accountnumber};
    }

    private void setupSpinner() {
        FromAccount = (Spinner) findViewById(R.id.AmountTosend);
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, accountnames);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        FromAccount.setAdapter(dataAdapter1);

    }

    public void getAllAccounts() {
        accountnames = new ArrayList<>();
        ServerGetRequest getAllAccounts = new ServerGetRequest("/getaccounts?Email=" + Email);
        accountObjecs = getAllAccounts.GetAllAccounobjects();

        for (int i = 0; i < accountObjecs.size(); i++) {
            accountnames.add(accountObjecs.get(i).getAccountName() + " (" + accountObjecs.get(i).getAccountType() + ")  Available:" + accountObjecs.get(i).getCurrentDeposit());

        }
    }


    private void Getvaluesfromsharedpref() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        Email = pref.getString("username", "");
        Log.d(Tag, Email + "<---Email from shared");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.Sendbutton:

                ServerPostRequest sendconformationcode = new ServerPostRequest("/sendserviceCode?Email="+Email);
                sendconformationcode.execute();



                if (checkemptyfields() && checkifAccountExist()) {

                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
                    LayoutInflater inflater = getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.nemidvalidation_fragment, null);
                    dialogView.findViewById(R.id.date).setVisibility(View.VISIBLE);
                    dialogView.findViewById(R.id.datetosend).setVisibility(View.VISIBLE);
                    dialogView.findViewById(R.id.pbsSetting).setVisibility(View.VISIBLE);
                    dialogView.findViewById(R.id.pbstextview).setVisibility(View.VISIBLE);

                    TextView fromAccount = dialogView.findViewById(R.id.fromAccount);
                    TextView accountType = dialogView.findViewById(R.id.accountType);
                    TextView accountNumber = dialogView.findViewById(R.id.accountNumber);
                    TextView registrationnumber = dialogView.findViewById(R.id.registrationnumber);
                    TextView txtToreceiver = dialogView.findViewById(R.id.txtToreceiver);
                    TextView amount = dialogView.findViewById(R.id.amount);
                    TextView exitdialog = dialogView.findViewById(R.id.exit_btn);
                    TextView pbs = dialogView.findViewById(R.id.pbsSetting);
                    TextView date = dialogView.findViewById(R.id.datetosend);
                    EditText conformationCode = dialogView.findViewById(R.id.conformationCode);

                    Button SendMoneyBtn = dialogView.findViewById(R.id.SendMoneyBtn);
                    dialogBuilder.setView(dialogView);
                    fromAccount.setText(accountObjecs.get(FromAccount.getSelectedItemPosition()).getAccountName());
                    accountType.setText(accountObjecs.get(FromAccount.getSelectedItemPosition()).getAccountType());
                    accountNumber.setText(accountnumber.getText().toString());
                    registrationnumber.setText(reg.getText().toString());
                    txtToreceiver.setText(txtToReceiver.getText().toString());
                    amount.setText(ammout.getText().toString());
                    pbs.setText(pbscheckbox.getText().toString());
                    date.setText(date.getText().toString());
                    AlertDialog dialog = dialogBuilder.create();


                    exitdialog.setOnClickListener(v1 -> dialog.dismiss());


                    SendMoneyBtn.setOnClickListener(v2 -> {

                        if (TextUtils.isEmpty(conformationCode.getText().toString())) {
                            conformationCode.setError("Required");
                            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();

                        } else {

                            ServerPostRequest paymoneybill = new ServerPostRequest("/paybill?Email=" + Email + "&TranceActionName=" + TransactionName.getText().toString() + "&fromAccount=" + accountObjecs.get(FromAccount.getSelectedItemPosition()).getAccountName() + "&accountnumber=" + accountnumber.getText().toString()
                                    + "&registrationNumber=" + reg.getText().toString() + "&value=" + ammout.getText().toString() + "&date=" + date.getText().toString() + "&pbs=" + pbscheckbox.isChecked() + "&servicecode=" + conformationCode.getText().toString());


                            if (paymoneybill.execute() == 200) {
                                Intent transationsactivity = new Intent(this, TransferMoneyMenu.class);
                                startActivity(transationsactivity);


                            } else {
                                Toast.makeText(this, "Error: Wrong conformations code", Toast.LENGTH_LONG).show();
                                conformationCode.setError("Wrong Code");
                            }


                        }


                    });
                    dialog.show();

                }



                break;

            case R.id.date:
                Usefulmethods calender = new Usefulmethods(date, this);
                calender.CalenderEdittext();
                break;

        }
    }




    private boolean checkifAccountExist() {

        ServerPostRequest checkBill = new ServerPostRequest("/checkbillsexist?digits=" + serialnumber.getSelectedItem().toString().substring(1) + "&accountnumber=" + accountnumber.getText().toString()
                + "&registrationNumber=" + reg.getText().toString());


        if (checkBill.getReponse() == 200) {
            Log.d(Tag, checkBill.getReponse().toString());
            return true;
        } else {

         Toast.makeText(this,"bill does'nst exist!!",Toast.LENGTH_LONG).show();
          reg.setError("check registration number again");
          accountnumber.setError("check account number number again");
        }
        return false;
    }


   public boolean checkemptyfields(){

       for (int i = 0; i <editTextsArray.length; i++) {

           if (TextUtils.isEmpty(editTextsArray[i].toString())){
               editTextsArray[i].setText(getString(R.string.requiredwarning));
               return false;

           }

       }
       return true;
   }

}