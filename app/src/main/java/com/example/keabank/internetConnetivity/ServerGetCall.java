package com.example.keabank.internetConnetivity;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;





public class ServerGetCall extends AsyncTask<String,String,String> {
    private String url;
    String Tag = "ServerGetCall";

    public ServerGetCall(String url) {
        this.url = url;
    }


    @Override
    protected String doInBackground(String... strings) {
        int responscode;
        String ip = GetServerIp.getInstance();
        String webapiadress = ip + url;
       Log.d(Tag,webapiadress);
        String reponse =null;
        URL url;

        try {
            url = new URL(webapiadress);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            BufferedReader bf = new BufferedReader(new InputStreamReader(con.getInputStream()));
            reponse = bf.readLine();
            responscode=con.getResponseCode();

            if(reponse==null) {
                String s = String.valueOf(responscode);
                Log.d(Tag, s + "<--reponsecode");
                return s;
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



       Log.d(Tag,reponse);
        return reponse;

    }
}





