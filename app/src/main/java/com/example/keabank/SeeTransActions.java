package com.example.keabank;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import com.example.keabank.internetConnetivity.ServerGetRequest;
import com.example.keabank.Model.Transactions;

import java.util.ArrayList;


public class SeeTransActions extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {
String Email,accountName;
String Tag="SeeTransActions";
    MyRecyclerViewAdapter adapter;
    ArrayList<Transactions> transActions;


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
              fillArrayList();
              status();
              inflatereclerview();





    }

    public void inflatereclerview(){

        RecyclerView recyclerView = findViewById(R.id.showTransactionsname);
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
            Log.d(Tag,transActions.get(i).getTransactionName());
        }
    }



    private void fillArrayList()  {

        transActions= new ArrayList<>();
        ServerGetRequest getTranstions = new ServerGetRequest("/AccountTransfers?Email="+Email+"&Accountname="+ accountName);
        transActions= getTranstions.GetAllTransActions();

    }


    @Override
    public void onItemClick(View view, int position) {

    }


}
