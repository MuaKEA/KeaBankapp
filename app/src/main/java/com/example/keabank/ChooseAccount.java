package com.example.keabank;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.example.keabank.Logic.ServerGetRequest;
import com.example.keabank.Model.Accounts;
import java.util.ArrayList;

public class ChooseAccount extends AppCompatActivity implements View.OnClickListener,MyRecyclerViewAdapter.ItemClickListener {
    MyRecyclerViewAdapter adapter;
    String Tag = "ChooseAccount";
    String Email;
    ArrayList<String> accountsNamesAndDeposit;
    ArrayList<Accounts> accountsobjects;
    Button createacoount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_account);
        Getvaluesfromsharedpref();
        startup();
        inflatereclerview();


  createacoount.setOnClickListener(this);



    }


    public void inflatereclerview(){

        RecyclerView recyclerView = findViewById(R.id.Aacounts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this,recyclerView.getId(),R.layout.recyclerview_row, accountsNamesAndDeposit);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }




    private void Getvaluesfromsharedpref() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        Email=pref.getString("username","");
        Log.d(Tag,Email + "<---Email from shared");

    }

    private void startup() {
        createacoount = findViewById(R.id.createaccount);

        getAllAccountsFromServer();

    }



    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this,CreateAccount.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this,SeeTransActions.class);
        intent.putExtra("accountname",accountsobjects.get(position).getAccountName());
        startActivity(intent);


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

