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
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ChooseAccount extends AppCompatActivity implements View.OnClickListener,MyRecyclerViewAdapter.ItemClickListener {
    MyRecyclerViewAdapter adapter;
    ArrayList<String> Accounts = new ArrayList<>();
    String Tag = "ChooseAccount";
    String Email, ip;
    ArrayList<String> accountsNames = new ArrayList<>();
    Button createacoount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_account);
        Getvaluesfromsharedpref();


        startup();
        inflatereclerview();
        giveStatusonArrayList();


  createacoount.setOnClickListener(this);



    }


    public void inflatereclerview(){

        RecyclerView recyclerView = findViewById(R.id.Aacounts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this,recyclerView.getId(),R.layout.recyclerview_row, Accounts);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }


    private void giveStatusonArrayList() {
        for (int i = 0; i <accountsNames.size() ; i++) {
            String a=Accounts.get(i);
            Log.d(Tag,i + "accountsNames-index->" + a + "<---");

        }
    }

    private void Getvaluesfromsharedpref() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        Email=pref.getString("username","");
        ip=pref.getString("Ip","");
        Log.d(Tag,Email + "<---Email from shared");
        Log.d(Tag,ip + "<---ip from shared");

    }

    private void startup() {

        createacoount=findViewById(R.id.createaccount);

        GetAccounts getAccounts = new GetAccounts();
        try {
            Accounts= getAccounts.execute().get();


        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(Tag,"arrayList is filled");

    }



    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this,CreateAccount.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this,SeeTransActions.class);
        intent.putExtra("accountname",accountsNames.get(position));
        Log.d(Tag,accountsNames.get(position));
        startActivity(intent);


    }



    public class GetAccounts extends AsyncTask<String,String,ArrayList<String>> {


        @Override
        protected ArrayList<String> doInBackground(String... strings) {

            ArrayList<String> AccountsFromServer= new ArrayList<>();
            String webapiadress = "http://"+ip+":8888/getaccounts?Email=" + Email;
            String reponse = "";

            URL url;
            try {
                url = new URL(webapiadress);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.connect();
                BufferedReader bf = new BufferedReader(new InputStreamReader(con.getInputStream()));
                reponse=bf.readLine();

                JSONObject myJsonResponse = new JSONObject(reponse);
                JSONArray jsonarray = myJsonResponse.getJSONArray("accountsList");

                for (int i = 0; i < jsonarray.length(); i++) {
                    JSONObject innerJsonObject= jsonarray.getJSONObject(i);
                    String account = innerJsonObject.getString("account");
                    String doposit = innerJsonObject.getString("currentdeposit");
                    Log.d(Tag,account +"<---accountname");
                    accountsNames.add(account);
                    AccountsFromServer.add(account +"\nBalance " + doposit);

                }


            } catch (Exception e) {
                e.printStackTrace();


            }


            return AccountsFromServer;
        }
    }

}

