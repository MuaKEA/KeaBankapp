package com.example.keabank.Logic;

import android.content.Context;
import android.util.Log;

import com.example.keabank.Model.Transactions;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class SavingAndReadingFiles {
private static String Tag="SavingAndReadingFiles";


    public static JSONArray readFileInternalStorage(Context context) {
        FileInputStream fileInputStream;


        try {
            fileInputStream = context.openFileInput("myfile.txt");
            InputStreamReader isr = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(isr);

            JSONArray jsonArray= new JSONArray(bufferedReader.readLine());
            Log.d(Tag,jsonArray.toString() + "<---reading JsonArray");





            return jsonArray;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JSONArray();
    }


    public static void saveToFile(Context context,Transactions content) {

        String filename = "myfile.txt";

        FileOutputStream outputStream;
        try {
            Gson gson = new Gson();
            JSONObject myJsonTransActions = new JSONObject(gson.toJson(content));

            JSONArray jsonArray= readFileInternalStorage(context);
            jsonArray.put(myJsonTransActions);

            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(jsonArray.toString().getBytes());
            outputStream.flush();
            outputStream.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

 public static void updatefile (Context context,JSONArray jsonArray){

     String filename = "myfile.txt";

     FileOutputStream outputStream;
     try {
         outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
         outputStream.write(jsonArray.toString().getBytes());
         outputStream.flush();
         outputStream.close();


     } catch (Exception e) {
         e.printStackTrace();
     }




 }








}
