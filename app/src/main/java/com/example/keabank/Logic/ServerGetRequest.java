package com.example.keabank.Logic;

import android.util.Log;

import com.example.keabank.Model.Accounts;
import com.example.keabank.Model.Transactions;
import com.example.keabank.internetConnetivity.ServerGetCall;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ServerGetRequest {

    private String operationName;
    private String url;
    private String Tag = "ServerGetRequest";
    private String reponse;



    public void execute(){

        reponse= ContactSever(url);
    }

    public ServerGetRequest(String url) {
        this.url = url;
         execute();
    }


    public ArrayList<Accounts> GetAllAccounobjects() {
        ArrayList<Accounts> accoutname = new ArrayList<>();

        try {


            JSONObject myJsonResponse = new JSONObject(reponse);
            JSONArray jsonarray = myJsonResponse.getJSONArray("accountsList");
            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject innerJsonObject = jsonarray.getJSONObject(i);
                String account = innerJsonObject.getString("account");
                double currentdeposit = innerJsonObject.getDouble("currentdeposit");
                String accounttype = innerJsonObject.getString("accounttype");
                Long accountNumber = innerJsonObject.getLong("accountNumber");
                Long registrationnumber = innerJsonObject.getLong("registrationnumber");

                 Log.d(Tag,account + " " + currentdeposit + " " + accounttype);
                accoutname.add(new Accounts(account,currentdeposit,accounttype,accountNumber,registrationnumber));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    return accoutname;
    }


    public ArrayList<Transactions> GetAllTransActions() {
        ArrayList<Transactions> transactionarrayList= new ArrayList<>();

        Log.d(Tag,reponse);
        try {

        JSONObject myJsonTransActions = new JSONObject(reponse);
        JSONArray transActions = myJsonTransActions.getJSONArray("transActions");

        for (int i = 0; i < transActions.length(); i++) {
        JSONObject innerJsonObject = transActions.getJSONObject(i);

        String transactionName = innerJsonObject.getString("transactionName");
        double dopositAfterTransaction = innerJsonObject.getDouble("dopositAfterTransaction");
        String transactionAmmount = innerJsonObject.getString("transactionAmmount");
        String date = innerJsonObject.getString("date");
        boolean sendingOrreciving = innerJsonObject.getBoolean("sendingOrreciving");
        String doubletostring;
        if (!sendingOrreciving) {
            doubletostring = "+" + transactionAmmount;

        } else {
            doubletostring = "-" + transactionAmmount;

        }
            transactionarrayList.add(new Transactions(transactionName,date,doubletostring,dopositAfterTransaction));
        }

        } catch (Exception e) {
        e.printStackTrace();


        }


        Log.d(Tag,transactionarrayList.toString());
        Log.d(Tag,transactionarrayList.size() +"<-transactionarrayList size");
         return transactionarrayList;
        }


        public String getCpr()  {
        JSONObject myJsonaccounttype;
        String cpr="";

        try {
            myJsonaccounttype = new JSONObject(reponse);

            cpr= myJsonaccounttype.getString("cpr");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cpr;
    }


    private String ContactSever(String url){
    String reponse="";
        ServerGetCall serverGetCall = new ServerGetCall(url);
    try {
        reponse=serverGetCall.execute().get();
    } catch (ExecutionException e) {
        e.printStackTrace();
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    return reponse;
}



}
