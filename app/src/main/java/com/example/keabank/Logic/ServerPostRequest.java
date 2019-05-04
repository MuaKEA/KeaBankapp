package com.example.keabank.Logic;

import android.util.Log;

import com.example.keabank.internetConnetivity.ServerGetCall;
import com.example.keabank.internetConnetivity.ServerPostCall;

import java.util.concurrent.ExecutionException;

public class ServerPostRequest {

    private String url;
    private String Tag = "ServerGetRequest";
    private Integer reponse;


    public ServerPostRequest(String url) {
        this.url = url;
         execute();

    }






    public Integer execute(){

        ServerPostCall serverGetCall = new ServerPostCall(url);
        try {
            reponse=serverGetCall.execute().get();
            Log.d(Tag,reponse.toString());


        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return reponse;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getReponse() {
        return reponse;
    }

    public void setReponse(Integer reponse) {
        this.reponse = reponse;
    }
}
