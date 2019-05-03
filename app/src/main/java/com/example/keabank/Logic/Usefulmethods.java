package com.example.keabank.Logic;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class Usefulmethods {
    private int mYear, mMonth, mDay;
    private EditText date;
    private Context context;
    private String Cpr;

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

        String format = "dd-MM-yyyy";
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.GERMAN);
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        Date dateTime = new GregorianCalendar(year, monthOfYear - 1, dayOfMonth).getTime();
                        simpleDateFormat.format(dateTime);

                        date.setText(simpleDateFormat.format(dateTime));

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();



    }
    public boolean ageChecker()  {

            //yyyy-moth-
            LocalDate l = LocalDate.of(Integer.valueOf("19" + Cpr.substring(4, 6)), Integer.valueOf(Cpr.substring(2, 4)), Integer.valueOf(Cpr.substring(0, 2))); //specify year, month, date directly
            LocalDate now = LocalDate.now(); //gets localDate
            Period diff = Period.between(l, now); //difference between the dates is calculated
            System.out.println(diff.getYears());

        if(diff.getYears() >=77){
            return true;

        }
         return false;

        }
}
