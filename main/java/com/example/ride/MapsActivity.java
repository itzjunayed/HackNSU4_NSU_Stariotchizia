package com.example.myapplication;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.SphericalUtil;

public class MapsActivity extends  AppCompatActivity {

    // initializing
    // FusedLocationProviderClient
    // object
    FusedLocationProviderClient mFusedLocationClient;
    Button clickme,delete,countbutton;
    AppCompatButton go;
    // Initializing other items
    // from layout file
    TextView latitudeTextView, longitTextView,value,flanvalue,flongvalue,slanvalue,slongvalue,mainridestart;
    LottieAnimationView lottieAnimationView;
    int PERMISSION_ID = 44;
    int count=0;
    int result=0;
    FirebaseDatabase database;

    double firstlan,firstlong,secondlang,secondlong;

    double langitude,longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        clickme=findViewById(R.id.click);
        value=findViewById(R.id.value);
        mainridestart=findViewById(R.id.mainridestart);
        delete=findViewById(R.id.delete);
        lottieAnimationView=findViewById(R.id.lottieanimationView);
        latitudeTextView = findViewById(R.id.latTextView);
        countbutton=findViewById(R.id.count);
        longitTextView = findViewById(R.id.lonTextView);
        flanvalue=findViewById(R.id.flanvalue);
        flongvalue=findViewById(R.id.flongvalue);
        slanvalue=findViewById(R.id.slanvalue);
        slongvalue=findViewById(R.id.slongvalue);
        go=findViewById(R.id.go);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // method to get the location

        getLastLocation();



        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.getReference().child("Currentvalue").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            String value=snapshot.getValue(String.class);

                            if(value.contains("start")){

                                Intent intent=new Intent(MapsActivity.this,qrcode.class);
                                startActivity(intent);

                            }else{

                                Intent intent=new Intent(MapsActivity.this,Fate.class);
                                startActivity(intent);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        database=FirebaseDatabase.getInstance("https://transport-d8ac9-default-rtdb.firebaseio.com/");
        clickme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                count=1;



                onResume();




                result++;

//                  if(result==1){
//                      firstlan=langitude;
//                      firstlong=longitude;
//                  }
//                if(result==2){
//                    secondlang=langitude;
//                    secondlong=longitude;
//                }
//
//                  if(result>=2){
//                       result=0;
//                  }
            }
        });



    }





    int  p=0;

    @SuppressLint("MissingPermission")
    public void getLastLocation() {
        // check if permissions are given
        if (checkPermissions()) {

            // check if location is enabled
            if (isLocationEnabled()) {

                // getting last
                // location from
                // FusedLocationClient
                // object
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {

                        Location location = task.getResult();


                        if (location == null) {
                            requestNewLocationData();
                        } else {




                            database.getReference().child("Currentvalue").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(snapshot.exists()){
                                        String value=snapshot.getValue(String.class);

                                        if(value.contains("start")){
                                            firstlan= langitude=location.getLatitude();
                                            firstlong=longitude=location.getLongitude();

                                            database.getReference().child("cordinate").child("firstlan").setValue(firstlan);
                                            database.getReference().child("cordinate").child("firstlong").setValue(firstlong);


                                        }else{
                                            go.setText("Show Fare");
                                            lottieAnimationView.setAnimationFromUrl("https://assets9.lottiefiles.com/packages/lf20_4yztayzg.json");
                                            mainridestart.setText("Your ride has ended to know the fare click below button");
                                            secondlang= langitude=location.getLatitude();
                                            secondlong=longitude=location.getLongitude();

                                            database.getReference().child("cordinate").child("secondlang").setValue(secondlang);
                                            database.getReference().child("cordinate").child("secondlong").setValue(secondlong);
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });





                            latitudeTextView.setText(location.getLatitude() + "");
                            longitTextView.setText(location.getLongitude() + "");
                        }
                    }
                });
            } else {
                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            // if permissions aren't available,
            // request for permissions
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    public void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    public LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();


        }
    };

    // method to check for permissions
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        // If we want background location
        // on Android 10.0 and higher,
        // use:
        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    // method to request for permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    // method to check
    // if location is enabled
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    // If everything is alright then
    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {

            requestNewLocationData();
            getLastLocation();
        }
    }

    @Override
    public void onBackPressed() {

    }
}