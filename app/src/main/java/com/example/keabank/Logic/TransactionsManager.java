package com.example.keabank.Logic;

import android.content.Context;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import static com.example.keabank.Logic.SavingAndReadingFiles.readFileInternalStorage;
import static com.example.keabank.Logic.SavingAndReadingFiles.updatefile;


public class TransactionsManager {
    private static String Tag = "TransactionsManager";




    public static void startTransactions(Context context) {

        
        try {

                        JSONArray jsonArray = readFileInternalStorage(context);

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
                                    updatefile(context, jsonArray);

                                    jsonArray.remove(i);
                                }
                            }


                        }




                } catch (JSONException e) {
                    e.printStackTrace();
                }

    }

}
