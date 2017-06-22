package com.child.tracking.system;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static com.child.tracking.system.R.id.map;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback{

    private static final int MY_LOCATION_REQUEST_CODE = 21;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Id = "stdIdKey";
    String topic="450";
    public static boolean active = true;
    private SQLiteDatabase db;
    Geocoder geocoder;
    List<Address> addresses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        topic=sharedpreferences.getString(Id,"450");
        Log.d("Value of topic",topic);
        FirebaseMessaging.getInstance().subscribeToTopic(topic);
        if(topic.equals("450")){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean permissionGranted = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        if(permissionGranted) {
            // {Some Code}
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
        }
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_LOCATION_REQUEST_CODE) {
            if (permissions.length == 1 &&
                    permissions[0] == android.Manifest.permission.ACCESS_FINE_LOCATION &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d("MapActivity", "onMapReady: ");
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (getIntent().getExtras() != null) {
                String lat = getIntent().getExtras().getString("latitude");
                String lng = getIntent().getExtras().getString("longitude");
                Log.d("hi",getIntent().getExtras().toString());
                geocoder = new Geocoder(MainActivity.this, Locale.ENGLISH);
                try {
                    addresses = geocoder.getFromLocation(Double.parseDouble(lat), Double.parseDouble(lng), 1);
                    Log.d("Location","is "+addresses.get(0).getAddressLine(0)+", "+addresses.get(0).getLocality());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                db=openOrCreateDatabase("PersonDB", Context.MODE_PRIVATE, null);
                db.execSQL("CREATE TABLE IF NOT EXISTS persons(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, latitude VARCHAR,longitude VARCHAR);");
                String query = "INSERT INTO persons (latitude,longitude) VALUES('"+lat+"', '"+lng+"');";
                db.execSQL(query);
                Toast.makeText(getApplicationContext(),"Saved Successfully", Toast.LENGTH_LONG).show();
                if (lat != null && lng != null) {
                    Double latitude = Double.parseDouble(lat);
                    Double longitude = Double.parseDouble(lng);
                    googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(latitude, longitude))
                            .title("Marker"));

                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(new LatLng(latitude, longitude))      // Sets the center of the map to location user
                            .zoom(15)                                     // Sets the zoom
                            .bearing(0)                                   // Sets the orientation of the camera to east
                            .tilt(30)                                       // Sets the tilt of the camera to 30 degrees
                            .build();                   // Creates a CameraPosition from the builder
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }
            }
            else {
                googleMap.setMyLocationEnabled(true);
            }
        }
    }
}
