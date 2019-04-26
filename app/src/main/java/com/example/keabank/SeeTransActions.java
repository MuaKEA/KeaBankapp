package com.example.keabank;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class SeeTransActions extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {
String Email,ip,accountName;
String Tag="SeeTransActions";
    MyRecyclerViewAdapter adapter;
    ArrayList<String> transAction = new ArrayList<>();


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
        inflatereclerview();
        status();


    }

    public void inflatereclerview(){

        RecyclerView recyclerView = findViewById(R.id.Transactions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this,recyclerView.getId(),R.layout.account_transactions_row, transAction);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    private void Getvaluesfromsharedpref() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        Email = pref.getString("username", "");
        ip = pref.getString("Ip", "");
        Log.d(Tag, Email + "<---Email from shared");
        Log.d(Tag, ip + "<---ip from shared");
    }


    private void status(){
        for (int i = 0; i <transAction.size() ; i++) {
            Log.d(Tag,transAction.get(i));
        }
    }

    private void fillArrayList() {

        GetTranstions getTranstions = new GetTranstions();
        try {
            transAction= getTranstions.execute().get();


        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i <transAction.size() ; i++) {
            Log.d(Tag,transAction.get(i) +"<-- index-->" + i);

        }

    }


    @Override
    public void onItemClick(View view, int position) {

    }

    public class GetTranstions extends AsyncTask<String,String,ArrayList<String>> {


        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            ArrayList<String> AccountsFromServer = new ArrayList<>();
            String webapiadress = "http://"+ip + ":8888/AccountTransfers?Email="+Email+"&Accountname="+ accountName;
            String reponse = "";

            URL url;
            try {
                url = new URL(webapiadress);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.connect();
                BufferedReader bf = new BufferedReader(new InputStreamReader(con.getInputStream()));
                reponse = bf.readLine();

                JSONObject myJsonResponse = new JSONObject(reponse);
                JSONArray jsonarray = myJsonResponse.getJSONArray("transActions");

                for (int i = 0; i < jsonarray.length(); i++) {
                    JSONObject innerJsonObject= jsonarray.getJSONObject(i);
                    String transactionName = innerJsonObject.getString("transactionName");
                    String dopositBeforeTransaction = innerJsonObject.getString("dopositBeforeTransaction");
                    String dopositAfterTransaction = innerJsonObject.getString("dopositAfterTransaction");
                    String date = innerJsonObject.getString("date");
                    boolean sendingOrreciving = innerJsonObject.getBoolean("sendingOrreciving");

                    if(!sendingOrreciving){
                        dopositBeforeTransaction="+" + dopositBeforeTransaction;

                    }else{
                        dopositBeforeTransaction="-" + dopositBeforeTransaction;

                    }






                    AccountsFromServer.add(transactionName + "\t\t\t" +date + "\n" +dopositBeforeTransaction  + "\n" + dopositAfterTransaction );

                }


            } catch (Exception e) {
                e.printStackTrace();


            }


            return AccountsFromServer;
        }
    }

}
