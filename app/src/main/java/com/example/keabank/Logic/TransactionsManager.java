package com.example.keabank.Logic;

import android.content.Context;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static com.example.keabank.Logic.SavingAndReadingFiles.updatefile;

public class TransactionsManager {
    private static String Tag = "TransactionsManager";
    private Context context;


    private TransactionsManager(Context context){
        this.context=context;
    }

    private TransactionsManager(){
    }

    private TransactionsManager transactionsManager= new TransactionsManager();






    public void startTransactions() {

        final FileInputStream[] fileInputStream = new FileInputStream[1];
        while (true) {
            Thread transactionsthread = new Thread(() -> {

                try {

                    fileExists();


                        fileInputStream[0] = context.openFileInput("myfile.txt");
                        InputStreamReader isr = new InputStreamReader(fileInputStream[0]);
                        BufferedReader bufferedReader = new BufferedReader(isr);
                        JSONArray jsonArray = new JSONArray(bufferedReader.readLine());

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject innerJsonObject = jsonArray.getJSONObject(i);
                            Long FromReg = innerJsonObject.getLong("FromReg");
                            Long ToReg = innerJsonObject.getLong("ToReg");
                            String date = innerJsonObject.getString("date");
                            LocalDate paydate = LocalDate.of(Integer.valueOf(date.substring(0, 4)), Integer.valueOf(date.substring(5, 7)), Integer.valueOf(date.substring(8, 10)));
                            Long fromAccountNumer = innerJsonObject.getLong("fromAccountNumer");
                            Long toAccountNumer = innerJsonObject.getLong("toAccountNumer");
                            String transactionAmmount = innerJsonObject.getString("transactionAmmount");
                            String transactionName = innerJsonObject.getString("transactionName");
                            String message = innerJsonObject.optString("message", "");

                            long daystopay = ChronoUnit.DAYS.between(LocalDate.now(), paydate);


                            if (daystopay == 0 || Long.toString(daystopay).contains("-")) {
                                ServerPostRequest transfermoney = new ServerPostRequest("/transaction?transationname=" + transactionName + "&text="
                                        + message + "&Freg=" + FromReg + "&FaccN=" + fromAccountNumer + "&Treg=" + ToReg + "&TaccN=" + toAccountNumer +
                                        "&amount=" + transactionAmmount);


                                if (transfermoney.execute() == 200) {

                                    jsonArray.remove(i);
                                }
                            }


                        }
                        updatefile(context, jsonArray);
                        Thread.sleep(60000);



                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            });
            transactionsthread.start();
        }
    }

    public  boolean fileExists() {
        File file = context.getFileStreamPath("myfile.txt");
        if(file == null || !file.exists()) {


            try {
               context.openFileOutput("myfile.txt",Context.MODE_PRIVATE);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Log.d(Tag,"filedoesnt exist");
            return false;
        }
        return true;
    }

    public TransactionsManager getinstance(Context context){

        return transactionsManager;

    }


    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
