package com.example.keabank;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.keabank.internetConnetivity.ServerPostCall;
import java.util.concurrent.ExecutionException;


public class CreateAccount extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
Button applybtn;
Spinner accountype,savingsspinner;
EditText accountname;
String Email;
String Tag = "CreateAccount";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);


        Getvaluesfromsharedpref();
        startup();

        applybtn.setOnClickListener(this);
        accountype.setOnItemSelectedListener(this);

    }

    private void startup() {
    applybtn = findViewById(R.id.applybtn);
    accountname=findViewById(R.id.accountname);
    accountype=findViewById(R.id.accountype);
    findViewById(R.id.automatictransfer).setVisibility(View.GONE);
        savingsspinner=findViewById(R.id.automatictransfer);
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
            saveSpecialAccount();
    }else
            saveAccount();
        startChooseAccountActivity();
    }

    private void saveSpecialAccount() {
    }

    private boolean noEmpthyfieldschecker() {

    if(accountname.getText().toString().equals("")){
       accountname.setError("Required");
        return false;
    }
    return true;
    }

    private void startChooseAccountActivity() {
    Intent intent = new Intent(this,ChooseAccount.class);
    startActivity(intent);


    }


  public int saveAccount()   {
        ServerPostCall saveaccount =  new ServerPostCall("/newAccount?Email="+Email+"&Accountname="+accountname.getText().toString()+"&AccountType=" + accountype.getSelectedItem().toString());
        try {
        
          return saveaccount.execute().get();
     
      } catch (ExecutionException e) {
          e.printStackTrace();
      } catch (InterruptedException e) {
          e.printStackTrace();
      }
      return 401;
  }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Object item = parent.getItemAtPosition(position);

    if(item.equals("Savings") || item.equals("Budget")){
        findViewById(R.id.automatictransfer).setVisibility(View.VISIBLE);
        Toast.makeText(this,"you need theese options",Toast.LENGTH_LONG).show();


    }else

        findViewById(R.id.automatictransfer).setVisibility(View.GONE);



    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}