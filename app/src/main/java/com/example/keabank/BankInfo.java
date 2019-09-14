package com.example.keabank;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.keabank.internetConnetivity.ServerGetRequest;
import com.example.keabank.Model.Accounts;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class BankInfo extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {
    TextView bankName, addr, City;
    Button ContactBank;
    String Email;
    String Tag = "Bankinfo";
    SupportMapFragment mapFragment;
    Accounts account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_info);

        init();
        Getvaluesfromsharedpref();
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        ContactBank.setOnClickListener(this);
        contactServer();

    }

    private void contactServer() {

        ServerGetRequest getaccounts=new ServerGetRequest(Email);


        ArrayList<Accounts> accounts=getaccounts.GetAllAccounobjects() ;

        if(accounts.size() !=0){

             account=accounts.get(0);

             if (account.getRegistrationnumber() == 4848){
                bankName.setText("ods filial");
                 addr.setText("næshovedvej 1");
                 City.setText("odense");


             }else {
                 bankName.setText("cph filial");
                 addr.setText("brønshøj 37");
                 City.setText("copenhagen");

             }

        }
    }


    private void init() {


        bankName = findViewById(R.id.bank_name);
        addr = findViewById(R.id.bank_adress);
        City = findViewById(R.id.bank_city);
        ContactBank = findViewById(R.id.bank_ContactBank);



    }

    private void Getvaluesfromsharedpref() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        Email = pref.getString("username", "");
        Log.d(Tag, Email + "<---Email from shared");

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.bank_ContactBank:
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:88998800"));
                startActivity(intent);

                break;





        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        Log.d(Tag,"hallo");
        LatLng odense = new LatLng(55.41637, 10.369008);
        LatLng cph = new LatLng(55.70545033, 12.49111176);

        googleMap.addMarker(new MarkerOptions().position(odense).title("kea bank odense"));
        googleMap.addMarker(new MarkerOptions().position(cph).title("kea bank copenhagen"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(odense));

    }
}
