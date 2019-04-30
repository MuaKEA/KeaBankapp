package com.example.keabank.internetConnetivity;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ServerGetCall extends AsyncTask<String,String,ArrayList<String>> {
private String url;
private String operationName;
ArrayList<String> Reponse = new ArrayList<>();
String Tag="ServerGetCall";

public ServerGetCall(String url,String operationName) {
this.url=url;
this.operationName=operationName;
}


    @Override
    protected ArrayList<String> doInBackground(String... strings) {

        String ip=GetServerIp.getInstance();
        String webapiadress = ip + url;
        String reponse="";
        URL url;
        Log.d("ServerGetCall",operationName);

        try {
            url = new URL(webapiadress);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            BufferedReader bf = new BufferedReader(new InputStreamReader(con.getInputStream()));
            reponse = bf.readLine();



            switch (operationName) {

                case "ResponseCode":
                    GetReponseCode(String.valueOf(con.getResponseCode()));
                    break;

                case "GetAllAccountNames":
                    GetAllAccountNames(reponse);
                    break;


                case "getAllAccountsAndDeposit":
                    getAllAccountsAndDeposit(reponse);

                    break;


                case "GetAllTransActions":
                    GetAllTransActions(reponse);



                    break;

            }
            } catch(Exception e){
                e.printStackTrace();

            }

     return Reponse;
    }

    private void getAllAccountsAndDeposit(String reponse) {
        Log.d(Tag,reponse);

        try {


            JSONObject myJsonResponse = new JSONObject(reponse);
            JSONArray jsonarray = myJsonResponse.getJSONArray("accountsList");
            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject innerJsonObject = jsonarray.getJSONObject(i);
                String account = innerJsonObject.getString("account");
                String deposit = innerJsonObject.getString("currentdeposit");
                String accounttype = innerJsonObject.getString("accounttype");
                Reponse.add(account + " " + deposit + "\nAccounttype: " + accounttype);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void GetAllAccountNames(String reponse) {
        try {


            JSONObject myJsonResponse = new JSONObject(reponse);
            JSONArray jsonarray = myJsonResponse.getJSONArray("accountsList");
            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject innerJsonObject = jsonarray.getJSONObject(i);
                String account = innerJsonObject.getString("account");
                Reponse.add(account);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

        private void GetAllTransActions(String reponse) {
            Log.d(Tag,reponse);
        try {

            JSONObject myJsonTransActions = new JSONObject(reponse);
            JSONArray transActions = myJsonTransActions.getJSONArray("transActions");

            for (int i = 0; i < transActions.length(); i++) {
                JSONObject innerJsonObject = transActions.getJSONObject(i);
                String transactionName = innerJsonObject.getString("transactionName");
                String dopositBeforeTransaction = innerJsonObject.getString("dopositBeforeTransaction");
                String dopositAfterTransaction = innerJsonObject.getString("dopositAfterTransaction");
                String date = innerJsonObject.getString("date");
                boolean sendingOrreciving = innerJsonObject.getBoolean("sendingOrreciving");

                if (!sendingOrreciving) {
                    dopositBeforeTransaction = "+" + dopositBeforeTransaction;

                } else {
                    dopositBeforeTransaction = "-" + dopositBeforeTransaction;

                }

                Reponse.add(transactionName + "\t\t\t" + date + "\n" + dopositBeforeTransaction + "\n" + dopositAfterTransaction);

            }


        } catch (Exception e) {
            e.printStackTrace();


        }

    }

    private void GetReponseCode(String ReposeCode) {
        Reponse.add(ReposeCode);
       Log.d(Tag,ReposeCode);
    }


}




