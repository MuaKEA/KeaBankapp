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

import com.example.keabank.Model.Accounts;
import com.example.keabank.internetConnetivity.ServerGetCall;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ChooseAccount extends AppCompatActivity implements View.OnClickListener,MyRecyclerViewAdapter.ItemClickListener {
    MyRecyclerViewAdapter adapter;
    String Tag = "ChooseAccount";
    String Email;
    ArrayList<String> accountsNamesAndDeposit;
    ArrayList<Accounts> accounts;
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

        createacoount=findViewById(R.id.createaccount);


        try {
            getAllAccountsFromServer();



        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }



    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this,CreateAccount.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this,SeeTransActions.class);
        intent.putExtra("accountname",accounts.get(position).getAccountName());
        startActivity(intent);


    }

public void getAllAccountsFromServer() throws ExecutionException, InterruptedException {

    accountsNamesAndDeposit= new ArrayList<>();
    ServerGetCall getAllAccounts = new ServerGetCall("/getaccounts?Email=" + Email,"getAllAccountsAndDeposit");
    accounts=getAllAccounts.execute().get();

    for (int i = 0; i <accounts.size() ; i++) {
        accountsNamesAndDeposit.add(accounts.get(i).getAccountName() +": " + accounts.get(i).getAccountType() +" " + accounts.get(i).getCurrentDeposit());
    }

    }
    }

