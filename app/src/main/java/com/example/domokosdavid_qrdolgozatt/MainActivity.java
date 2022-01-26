package com.example.domokosdavid_qrdolgozatt;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
                intentIntegrator.setPrompt("QR Kód szkenner");
                intentIntegrator.setBeepEnabled(false);
                //Elindítás
                intentIntegrator.initiateScan();
            }
        });




    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (result != null){
            String seged = textViewKiir.getText().toString();
            if (result.getContents() == null){
                Toast.makeText(this,"Kiléptél a scannelésből!", Toast.LENGTH_SHORT).show();
            }
            else{
                textViewKiir.setText(result.getContents());

                try {
                    Uri uri = Uri.parse(seged);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }catch (Exception exception){
                    Log.d("URI ERROR", exception.toString());
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void fajlbair(String szoveg) throws IOException {
        Date datum = Calendar.getInstance().getTime();
        String formatum = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(datum);
        String sor = String.format("%s, %s", formatum, szoveg);
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)){
            File file = new File(Environment.getExternalStorageDirectory(), "scannedCodes.csv");
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));

            bufferedWriter.append(sor);
            bufferedWriter.append(System.lineSeparator());
            bufferedWriter.close();
        }
    }

    public void init(){
        buttonScann = findViewById(R.id.buttonQRScan);
        buttonKiir = findViewById(R.id.buttonQRKiir);
        textViewKiir = findViewById(R.id.textViewKiir);
    }
}