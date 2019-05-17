package com.example.keabank;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.keabank.Logic.ServerGetRequest;
import com.example.keabank.Logic.ServerPostRequest;
import com.example.keabank.Model.Accounts;
import java.time.LocalDate;
import java.util.ArrayList;


public class CreateAccount extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
Button applybtn;
Spinner accountype,automatictransfer,Faccount;
EditText accountname,automatictranferamount;
String Email;
String Tag = "CreateAccount";
TextView tx1,tx2;
ArrayList<String>accountsNamesAndDeposit;
ArrayList<Accounts> accountsobjects;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        Getvaluesfromsharedpref();
        startup();
        getAllAccountsFromServer();
        statusOnarrayList();
        setupSpinner();

        applybtn.setOnClickListener(this);
        accountype.setOnItemSelectedListener(this);

    }

    private void statusOnarrayList() {
       Log.d(Tag,accountsNamesAndDeposit.size() + "<<--size");



    }

    private void startup() {
        makeitemsGone();
    applybtn = findViewById(R.id.applybtn);
    accountname=findViewById(R.id.accountname);
    accountype=findViewById(R.id.accountype);
    automatictransfer=findViewById(R.id.moneysendingplan);
    tx1=findViewById(R.id.txt1);
    tx2=findViewById(R.id.txt2);
    automatictranferamount=findViewById(R.id.automatictranferamount);




    }

    private void setupSpinner() {
        Faccount = findViewById(R.id.Faccount);
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, accountsNamesAndDeposit);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Faccount.setAdapter(dataAdapter1);

    }


    private void Getvaluesfromsharedpref() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        Email=pref.getString("username","");
        Log.d(Tag,Email + "<---Email from shared");

    }

    @Override
    public void onClick(View v) {

        if(accountype.getSelectedItem().equals("Pension")){
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("Pension account can only deposit money, and can only be withdrawn at the age of 77. \n press yes to create the account or press no to cancel");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Yes",
                    (dialog, id) -> {
                        dialog.cancel();

                        if(noEmpthyfieldschecker() && saveAccount() == 200){
                            startChooseAccountActivity();

                            }


                    });

            builder1.setNegativeButton(
                    "No",
                    (dialog, id) -> dialog.cancel());

            AlertDialog alert11 = builder1.create();
            alert11.show();





    }else if(accountype.getSelectedItem().toString().equals("Savings") || accountype.getSelectedItem().toString().equals("Budget")) {

            if(noEmpthyfieldschecker())

            saveSpecialAccount();




    }else



            if(saveAccount()==200){
                startChooseAccountActivity();



            }else
                Toast.makeText(this,"something went Wrong",Toast.LENGTH_LONG).show();

    }

    private void saveSpecialAccount()  {
        LocalDate today = LocalDate.now();
                                    //http://10.149.88.167:8888/SaveSavingsAccount?Email=altair2400@gmail.com&Accountname=haihalo&AccountType=Monthlytest&Fromaccount=Keabank&ammount=2&date=2019-05-01&automatedsetting=date
    ServerPostRequest saveaccount=new ServerPostRequest("/SaveSavingsAccount?Email="+Email + "&Accountname=" +accountname.getText().toString()+"" +"&AccountType=" + accountype.getSelectedItem().toString()+
            "&Fromaccount="+accountsobjects.get(Faccount.getSelectedItemPosition()).getAccountName() +"&ammount=" +automatictranferamount.getText().toString() +"&date="+ today.toString()
        + "&automatedsetting=" +automatictransfer.getSelectedItem().toString());


    if(saveaccount.execute() ==200){
        startChooseAccountActivity();

    }else
        Toast.makeText(this,"Error some bad happend",Toast.LENGTH_LONG).show();

    }

    private boolean noEmpthyfieldschecker() {

    if(!accountype.getSelectedItem().toString().equals("Savings") && !accountype.getSelectedItem().toString().equals("Budget") && accountname.getText().toString().equals("")){
       accountname.setError("Required");
        return false;


    }else if(accountype.getSelectedItem().toString().equals("Savings") || accountype.getSelectedItem().toString().equals("Budget")){

        if(accountname.getText().toString().equals("") && automatictranferamount.getText().toString().equals("")){
            accountname.setError("Required");
            automatictranferamount.setError("Required");
            return false;
        }


    }

    return true;






    }

    private void startChooseAccountActivity() {
    Intent intent = new Intent(this,ChooseAccount.class);
    startActivity(intent);


    }

    public int saveAccount()   {
        ServerPostRequest saveaccount =  new ServerPostRequest("/newAccount?Email="+Email+"&Accountname="+accountname.getText().toString()+"&AccountType=" + accountype.getSelectedItem().toString());
        saveaccount.execute();


          return saveaccount.getReponse();
     



  }

  @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Object item = parent.getItemAtPosition(position);

    if(item.equals("Savings") || item.equals("Budget")){
        makeitemsVisible();
        Toast.makeText(this,"you need theese options",Toast.LENGTH_LONG).show();


    }else
        makeitemsGone();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

   public void makeitemsVisible(){

       findViewById(R.id.moneysendingplan).setVisibility(View.VISIBLE);
       findViewById(R.id.txt1).setVisibility(View.VISIBLE);
       findViewById(R.id.txt2).setVisibility(View.VISIBLE);
       findViewById(R.id.Faccount).setVisibility(View.VISIBLE);
       findViewById(R.id.automatictranferamount).setVisibility(View.VISIBLE);


   }

 public void makeitemsGone(){
     findViewById(R.id.txt2).setVisibility(View.GONE);
     findViewById(R.id.txt1).setVisibility(View.GONE);
     findViewById(R.id.Faccount).setVisibility(View.GONE);
     findViewById(R.id.automatictranferamount).setVisibility(View.GONE);
     findViewById(R.id.moneysendingplan).setVisibility(View.GONE);

 }

    public void getAllAccountsFromServer() {

        accountsNamesAndDeposit= new ArrayList<>();
        ServerGetRequest getAllAccounts = new ServerGetRequest("/getaccounts?Email=" + Email);

       accountsobjects=getAllAccounts.GetAllAccounobjects();
        for (int i = 0; i <accountsobjects.size() ; i++) {
            accountsNamesAndDeposit.add(accountsobjects.get(i).getAccountName() +" (" +accountsobjects.get(i).getAccountType() +  ")\navailable:" + accountsobjects.get(i).getCurrentDeposit());


        }


    }
}