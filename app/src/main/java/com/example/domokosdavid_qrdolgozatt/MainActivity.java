package com.example.domokosdavid_qrdolgozatt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;

public class MainActivity extends AppCompatActivity {

    private Button buttonScann, buttonKiir;
    private TextView textViewKiir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        buttonScann.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.this);
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                intentIntegrator.setPrompt("QR Code Scanner by Petrik");
                intentIntegrator.setBeepEnabled(false);
                //Elindítás
                intentIntegrator.initiateScan();
            }
        });
    }

    public void init(){
        buttonScann = findViewById(R.id.buttonQRScan);
        buttonKiir = findViewById(R.id.buttonQRKiir);
    }
}