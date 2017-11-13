package com.example.andrewjr.cacheme;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class login extends AppCompatActivity {
    private String TAG = "cacheMe";
    private Button loginButton;
    private Button registerButton;
    EditText usernameTxt;
    EditText passwordTxt;
//    Context context = getApplicationContext();
    int duration = Toast.LENGTH_SHORT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameTxt = (EditText) findViewById(R.id.username_intxt);
        passwordTxt = (EditText) findViewById(R.id.password_intxt);
        usernameTxt.setText("andrew");
        passwordTxt.setText("a123");
        registerButton = (Button) findViewById(R.id.register_link_button);
        loginButton = (Button) findViewById(R.id.button_login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG,"Button Clicked");
                String username = usernameTxt.getText().toString().toLowerCase();
                String password = passwordTxt.getText().toString().toLowerCase();

                validateUser(username, password);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this, register_user.class);
                startActivity(intent);
            }
        });
    }

    private void validateUser(final String username, final String password){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference().child("Users").child(username);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener(){

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    GenericTypeIndicator<Map<String, String>> genericTypeIndicator = new GenericTypeIndicator<Map<String, String>>() {
                    };
                    Map<String, String> map = dataSnapshot.getValue(genericTypeIndicator);
                    String databasePassword = map.get("password");
                    Log.i(TAG, databasePassword);
                    if(password.equals(databasePassword)){
                        Intent intent = new Intent(login.this,user_profile.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "Login Successful", duration).show();
                    }else{
                        usernameTxt.setText("");
                        passwordTxt.setText("");
                        Toast.makeText(getApplicationContext(), "Invalid Password, Try again", duration).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid Username, Try again", duration).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
