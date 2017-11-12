package com.example.andrewjr.cacheme;

import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

public class available_slots extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String TAG = "cacheMe";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_slots);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        availableSlotsFromFirebase();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }


    private void availableSlotsFromFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference().child("AvailableSlots");

        // Read from the database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<Map<String, Map<String, String>>> genIndicator = new GenericTypeIndicator<Map<String, Map<String, String>>>() {
                };
                Map<String, Map<String, String>> usersMap = dataSnapshot.getValue(genIndicator);
                ArrayList<AvailableSlots> availableSlotsArrayList = new ArrayList<>();
                for (Map.Entry<String, Map<String, String>> entry : usersMap.entrySet()) {
                    Log.i(TAG, entry.getKey());
                    Map<String, String> userDetails = entry.getValue();
                    AvailableSlots availableSlots = new AvailableSlots();
                    for (Map.Entry<String, String> subEntry : userDetails.entrySet()) {
                        if (subEntry.getKey().equals("available")) {
                            Log.i(TAG, subEntry.getValue());
                            availableSlots.setAvailable(subEntry.getValue());
                        } else if (subEntry.getKey().equals("lat")) {
                            Log.i(TAG, subEntry.getValue());
                            availableSlots.setLat(subEntry.getValue());
                        } else if (subEntry.getKey().equals("long")) {
                            Log.i(TAG, subEntry.getValue());
                            availableSlots.setLongi(subEntry.getValue());
                        } else if (subEntry.getKey().equals("pricePerHour")) {
                            Log.i(TAG, subEntry.getValue());
                            availableSlots.setPricePerHour(subEntry.getValue());
                        }
                    }
                    Log.i(TAG, availableSlots.getPricePerHour());
                    Log.i(TAG, availableSlots.getAvailable().toString());
                    Log.i(TAG, availableSlots.getLat().toString());
                    Log.i(TAG, availableSlots.getLongi().toString());
                    availableSlotsArrayList.add(availableSlots);
                }
                addMarker(availableSlotsArrayList);


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private void addMarker(ArrayList<AvailableSlots> availableSlotsArrayList) {
        MarkerOptions markerOptions;
        for (AvailableSlots obj : availableSlotsArrayList) {
            LatLng tempLocation = new LatLng(obj.getLat(), obj.getLongi());
            if (obj.getAvailable()) {
                markerOptions = new MarkerOptions().position(tempLocation).title("Available : " + obj.getPricePerHour()+ " $ per hour ").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            } else {
                markerOptions = new MarkerOptions().position(tempLocation).title("Sorry I'm already Booked").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            }
            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tempLocation,14.0f));
        }
    }
}
