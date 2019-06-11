package com.example.keabank;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.keabank.Logic.ServerGetRequest;
import com.example.keabank.Model.Accounts;

import java.util.List;

public class Account extends AppCompatActivity implements AccountsRecyclerview.ItemClickListener {

    List<Accounts> accountsList;
    AccountsRecyclerview adapter;
    String Email;
    String Tag="Account";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Getvaluesfromsharedpref();
        getaccountsFromServer();
        setuprecyclerview();


    }

    private void getaccountsFromServer() {
        ServerGetRequest serverGetRequest = new ServerGetRequest(Email);
        accountsList= serverGetRequest.GetAllAccounobjects();


    }

    private void Getvaluesfromsharedpref() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        Email = pref.getString("username", "");
        Log.d(Tag, Email + "<---Email from shared");
    }


    private void setuprecyclerview() {
        RecyclerView recyclerView = findViewById(R.id.accountsrecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AccountsRecyclerview(this,recyclerView.getId(),R.layout.acounts_activity_rows, accountsList);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

    }


    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this,SeeTransActions.class);
        intent.putExtra("accountname",accountsList.get(position).getAccountName());
        startActivity(intent);


    }
}
