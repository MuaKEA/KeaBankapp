package com.example.keabank;

import android.content.Intent;
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

public class CreateAccount extends AppCompatActivity implements View.OnClickListener,MyRecyclerViewAdapter.ItemClickListener {
    MyRecyclerViewAdapter adapter;
   static ArrayList<String> Accounts=new ArrayList<>();
   String Tag="CreateAccount";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
       fillArrayList();



        RecyclerView recyclerView = findViewById(R.id.rvAnimals);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, Accounts);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

    }

    private void fillArrayList() {
        if(Accounts.size()==0) {
            GetAccounts getAccounts = new GetAccounts();
            getAccounts.execute();
            Log.d(Tag,"arrayList is filled");

        }
        }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(View view, int position) {




    }



public class GetAccounts extends AsyncTask<String,String,String>{

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }



    @Override
    protected String doInBackground(String... strings) {
        String webapiadress = "http://10.149.88.167:8888/getaccounts?Email=altair2400@gmail.com";
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
                Log.d(Tag,account + " " + doposit);

                Accounts.add(account + " " + doposit);

            }


        } catch (Exception e) {
            e.printStackTrace();


        }


        return null;
    }
}

}
