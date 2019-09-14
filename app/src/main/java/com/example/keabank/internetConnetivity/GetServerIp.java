package com.example.keabank.internetConnetivity;

public class GetServerIp {
    private String ip;


    private static final GetServerIp ourInstance = new GetServerIp("http://Keabankaws-env.q4uhvhkr2z.eu-central-1.elasticbeanstalk.com");

    public static String getInstance() {
        return ourInstance.ip;
    }

    private GetServerIp(String ip) {
    this.ip=ip;

    }
}
