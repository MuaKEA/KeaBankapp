package com.example.keabank.Logic;

import android.os.AsyncTask;
import android.util.Log;

import com.example.keabank.Model.Accounts;
import com.example.keabank.Model.Transactions;
import com.example.keabank.internetConnetivity.GetServerIp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ServerGetRequest extends AsyncTask<String,String,String> {

    private String TAG = "ServerGetRequest";
    private String Url;


    public ServerGetRequest(String Url){
        this.Url=Url;

    }



    public ArrayList<Accounts> GetAllAccounobjects() {
        ArrayList<Accounts> accoutname = new ArrayList<>();

        try {
           String reponse= execute("/getaccounts?Email=" +Url).get();

            Log.d(TAG, "GetAllAccounobjects: "+ reponse.length());
            JSONObject myJsonResponse = new JSONObject(reponse);
            JSONArray jsonarray = myJsonResponse.getJSONArray("accountsList");
            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject innerJsonObject = jsonarray.getJSONObject(i);
                String account = innerJsonObject.getString("account");
                double currentdeposit = innerJsonObject.getDouble("currentdeposit");
                String accounttype = innerJsonObject.getString("accounttype");
                Long accountNumber = innerJsonObject.getLong("accountNumber");
                Long registrationnumber = innerJsonObject.getLong("registrationnumber");

                 Log.d(TAG,account + " " + currentdeposit + " " + accounttype);
                accoutname.add(new Accounts(account,currentdeposit,accounttype,accountNumber,registrationnumber));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return accoutname;
    }


    public ArrayList<Transactions> GetAllTransActions() {
        ArrayList<Transactions> transactionarrayList= new ArrayList<>();
        try {
            String reponse=execute(Url).get();


            Log.d(TAG,reponse);
            JSONObject myJsonTransActions = new JSONObject(reponse);
            JSONArray transActions = myJsonTransActions.getJSONArray("transActions");

            Log.d(TAG, "GetAllTransActions: " + reponse);

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


        Log.d(TAG,transactionarrayList.toString());
        Log.d(TAG,transactionarrayList.size() +"<-transactionarrayList size");
         return transactionarrayList;
        }


        public String getCpr()  {
        JSONObject myJsonaccounttype;
        String cpr="";
        try {
            myJsonaccounttype = new JSONObject(execute("/getaccounts?Email="+ Url).get());

            cpr= myJsonaccounttype.getString("cpr");

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
            return cpr;
    }


    @Override
    protected String doInBackground(String...strings) {
        String ip = GetServerIp.getInstance();
        String webapiadress = ip + strings[0];
        Log.d(TAG,webapiadress +"<--doinbackground");
        URL url;
        String reponse =null;
        try {
            url = new URL(webapiadress);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            BufferedReader bf = new BufferedReader(new InputStreamReader(con.getInputStream()));
            reponse = bf.readLine();
            con.disconnect();
            Log.d(TAG, "doInBackground: " + reponse);
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



        Log.d(TAG,reponse);


        return reponse;
    }
}
