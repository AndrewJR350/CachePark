package com.example.andrewjr.cacheme;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class register_user extends AppCompatActivity {
    private String TAG = "cacheMe";
//    Context context = getApplicationContext();
    int duration = Toast.LENGTH_SHORT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        Button registerButton = (Button) findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
        registerEvent();
            }
        });
    }

    private void writeIntoFirebase(String name, String phoneNo, String username, String password)  {
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users");

        myRef.child(username).child("Name").setValue(name);
        myRef.child(username).child("phoneNo").setValue(phoneNo);
        myRef.child(username).child("username").setValue(username);
        myRef.child(username).child("password").setValue(password);

        Intent intent = new Intent(register_user.this, user_profile.class);
        startActivity(intent);

        Toast.makeText(getApplicationContext(), "Successfully Registered", duration).show();
    }

    private void registerEvent() {
        EditText nameTxt = (EditText) findViewById(R.id.name_intxt);
        EditText phoneNoTxt = (EditText) findViewById(R.id.phone_intxt);
        EditText usernameTxt = (EditText) findViewById(R.id.username_intxt);
        EditText passwordTxt = (EditText) findViewById(R.id.password_intxt);

        String username = usernameTxt.getText().toString().toLowerCase();
        String password = passwordTxt.getText().toString().toLowerCase();
        String name = nameTxt.getText().toString().toLowerCase();
        String phoneNo = phoneNoTxt.getText().toString().toLowerCase();

        writeIntoFirebase(name, phoneNo, username, password);
    }
}
