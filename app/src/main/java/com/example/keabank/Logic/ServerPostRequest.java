package com.example.keabank.Logic;


import com.example.keabank.internetConnetivity.ServerPostCall;

import java.util.concurrent.ExecutionException;

public class ServerPostRequest {

    private String url;
    private String Tag = "ServerGetRequest";
    private Integer reponse;


    public ServerPostRequest(String url) {
        this.url = url;


    }

    public Integer execute(){

        ServerPostCall serverGetCall = new ServerPostCall(url);
        try {
            reponse=serverGetCall.execute().get();


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
