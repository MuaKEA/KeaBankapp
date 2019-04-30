package com.example.keabank.internetConnetivity;

public class GetServerIp {
    private String ip;


    private static final GetServerIp ourInstance = new GetServerIp("http://10.149.88.167:8888");

    public static String getInstance() {
        return ourInstance.ip;
    }

    private GetServerIp(String ip) {
    this.ip=ip;

    }
}
