package com.example.keabank.Logic;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.keabank.Model.Transactions;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class Usefulmethods {
    private int mYear, mMonth, mDay;
    private EditText date;
    private Context context;
    private String Cpr;
    private String Tag="TransferMoneyToAccount";



    public Usefulmethods(EditText date,Context context){
        this.date=date;
        this.context=context;
    }

    public Usefulmethods(String Cpr){
        this.Cpr=Cpr;
    }


    public void CalenderEdittext(){
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        String format = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.GERMAN);
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                (view, year, monthOfYear, dayOfMonth) -> {
                    Date dateTime = new GregorianCalendar(year, monthOfYear , dayOfMonth).getTime();

                    date.setText(simpleDateFormat.format(dateTime));

                }, mYear, mMonth, mDay);
        datePickerDialog.show();



    }

    public boolean ageChecker()  {

            LocalDate l = LocalDate.of(Integer.valueOf("19" + Cpr.substring(4, 6)), Integer.valueOf(Cpr.substring(2, 4)), Integer.valueOf(Cpr.substring(0, 2))); //specify year, month, date directly
            LocalDate now = LocalDate.now(); //gets localDate
            Period diff = Period.between(l, now); //difference between the dates is calculated
            System.out.println(diff.getYears());

        return diff.getYears() >= 77;

    }




}
