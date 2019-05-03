package com.example.keabank.internetConnetivity;

import android.os.AsyncTask;
import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;

public class ServerPostCall extends AsyncTask<String,String,Integer> {
private String url;

public ServerPostCall(String url){
    this.url=url;

}

    @Override
    protected Integer doInBackground(String... strings) {
        String ip=GetServerIp.getInstance();
        String webapiadress = ip + url;
        Integer reponse=null;

        URL url;
        try {
            url = new URL(webapiadress);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.connect();
            reponse = con.getResponseCode();


        } catch (Exception e) {
            e.printStackTrace();


        }

        return reponse;

    }
}
