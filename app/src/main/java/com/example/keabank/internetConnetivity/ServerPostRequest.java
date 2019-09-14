package com.example.keabank.internetConnetivity;


import android.os.AsyncTask;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public  class ServerPostRequest extends AsyncTask<String,String,Integer> {

    private String url;
    private String Tag = "ServerGetRequest";
    private Integer reponse;

    public ServerPostRequest(String url) {
        this.url = url;


    }




    public Integer execute(){
        try {
            reponse=execute(url).get();


        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return reponse;
    }



    public Integer getReponse() {
        return reponse;
    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }



    @Override
    protected Integer doInBackground(String... strings) {
        String ip= GetServerIp.getInstance();
        String webapiadress = ip + url;
        Integer reponse=null;
        URL url;
        try {
            url = new URL(webapiadress);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.connect();
            reponse = con.getResponseCode();
            con.disconnect();


            if(reponse==200){
                return reponse;
            }else{
                return 401;
            }

        } catch (Exception e) {
            e.printStackTrace();


        }




        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
    }
}
