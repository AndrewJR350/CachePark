package com.example.andrewjr.cacheme;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;

public class register_slot extends AppCompatActivity {
    int duration = Toast.LENGTH_SHORT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_slot);

        Button registerButton = (Button) findViewById(R.id.btnRegisterSlot);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                registerEvent();
            }
        });

    }

    private void registerEvent() {
        EditText usernameTxt = (EditText) findViewById(R.id.txtUsername);
        EditText locationTxt = (EditText) findViewById(R.id.txtAddress);
        EditText priceTxt = (EditText) findViewById(R.id.txtPrice);

        String username = usernameTxt.getText().toString().toLowerCase();
        String location = locationTxt.getText().toString().toLowerCase();
        String price = priceTxt.getText().toString().toLowerCase();
        Double lat = 0.0;
        Double longi = 0.0;
        List<Address> addressList = null;
        if (location != null || !location.equals("")) {
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(location, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Address address = addressList.get(0);
            lat = address.getLatitude();
            longi = address.getLongitude();
        }

        writeIntoFirebase(username, price, String.valueOf(lat), String.valueOf(longi));
    }

    private void writeIntoFirebase(String username, String price, String lat, String longi) {
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("AvailableSlots");

        myRef.child(username).child("pricePerHour").setValue(price);
        myRef.child(username).child("lat").setValue(lat);
        myRef.child(username).child("long").setValue(longi);
        myRef.child(username).child("available").setValue("true");


        Intent intent = new Intent(register_slot.this, available_slots.class);
        startActivity(intent);

        Toast.makeText(getApplicationContext(), "Successfully Registered", duration).show();
    }
}
