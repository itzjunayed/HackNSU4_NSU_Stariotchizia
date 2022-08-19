package com.example.myapplication;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.Captureact;
import com.example.myapplication.R;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.integration.android.IntentIntegrator;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class qrcode extends AppCompatActivity {
    Button button;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        button=findViewById(R.id.scan);
        database=FirebaseDatabase.getInstance("https://transport-d8ac9-default-rtdb.firebaseio.com/");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanCode();
            }
        });
    }


    private  void scanCode(){

        ScanOptions option=new ScanOptions();
        option.setPrompt("volume to flash on");
        option.setBeepEnabled(true);
        option.setOrientationLocked(true);
        option.setCaptureActivity(Captureact.class);

        barlauncher.launch(option);
    }
    ActivityResultLauncher<ScanOptions> barlauncher=registerForActivityResult(new ScanContract(),result -> {

        if(result.getContents()!=null){
            AlertDialog.Builder builder=new AlertDialog.Builder(qrcode.this);
            builder.setTitle("Result");
            builder.setMessage(result.getContents());

            if(result.getContents().contains("Raida")){
                Intent intent=new Intent(qrcode.this,MapsActivity.class);
                startActivity(intent);
                database.getReference().child("Currentvalue").setValue("start");

            }else{
                database.getReference().child("Currentvalue").setValue("end");
                Intent intent=new Intent(qrcode.this,MapsActivity.class);
                startActivity(intent);
                Toast.makeText(qrcode.this,"Bad Joke",Toast.LENGTH_LONG).show();
            }


        }

    });



}