package com.example.keabank;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import com.example.keabank.internetConnetivity.ServerGetCall;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class SeeTransActions extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {
String Email,accountName;
String Tag="SeeTransActions";
    MyRecyclerViewAdapter adapter;
    ArrayList<String> transActions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seetransactions);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null) {
           accountName=extras.getString("accountname");
            Log.d(Tag,"inside extra's" + accountName);
        }

    Getvaluesfromsharedpref();
        try {
            fillArrayList();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        inflatereclerview();
        status();


    }

    public void inflatereclerview(){

        RecyclerView recyclerView = findViewById(R.id.Transactions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this,recyclerView.getId(),R.layout.account_transactions_row, transActions);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    private void Getvaluesfromsharedpref() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        Email = pref.getString("username", "");
        Log.d(Tag, Email + "<---Email from shared");
    }


    private void status(){
        for (int i = 0; i <transActions.size() ; i++) {
            Log.d(Tag,transActions.get(i));
        }
    }



    private void fillArrayList() throws ExecutionException, InterruptedException {

        ServerGetCall getTranstions = new ServerGetCall("/AccountTransfers?Email="+Email+"&Accountname="+ accountName,"GetAllTransActions");

        transActions=getTranstions.execute().get();

    }


    @Override
    public void onItemClick(View view, int position) {

    }


}
