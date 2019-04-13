package com.example.keabank;

import android.os.AsyncTask;
import android.service.autofill.TextValueSanitizer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ApiCallActivity extends AppCompatActivity {
String webApiAddress;
TextView apiResponce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_call);
        Button apiRequest = (Button)findViewById(R.id.apiCall);
        final TextView apiCity = findViewById(R.id.apiCity);
        apiResponce = findViewById(R.id.apiResponse);

        apiRequest.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                webApiAddress ="https://api.openweathermap.org/data/2.5/weather?q=" +apiCity.getText() + "&APPID=bc0f58134016ebbb041d2cfedffa974c&units=metric";
                ApiCall apiCall = new ApiCall();
                apiCall.execute();
            }
        });
    }

public class ApiCall extends AsyncTask<String,String,String>{
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @Override
    protected String doInBackground(String... strings) {
        return null;
    }
}

}
