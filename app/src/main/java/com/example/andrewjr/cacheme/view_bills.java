package com.example.andrewjr.cacheme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class view_bills extends AppCompatActivity {
    private String TAG = "cacheMe";
    private ListView billsListView;
    private List<String> billList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bills);
        billsListView = (ListView) findViewById(R.id.listview_bills);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, billList);
        billsListView.setAdapter(arrayAdapter);

        getAllBill();
        arrayAdapter.notifyDataSetChanged();
    }


    private void getAllBill() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Bills").child("andrew");


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if (dataSnapshot.exists()) {
                    billList.clear();
                    GenericTypeIndicator<Map<String, Map<String, String>>> genIndicator = new GenericTypeIndicator<Map<String, Map<String, String>>>() {
                    };
                    Map<String, Map<String, String>> usersMap = dataSnapshot.getValue(genIndicator);
                    ArrayList<Bills> billsArrayList = new ArrayList<>();
                    for (Map.Entry<String, Map<String, String>> entry : usersMap.entrySet()) {
                        Log.i(TAG, entry.getKey());

                        Map<String, String> userDetails = entry.getValue();
                        Bills bills = new Bills();
                        for (Map.Entry<String, String> subEntry : userDetails.entrySet()) {
                            Log.i(TAG, subEntry.getKey() + " " + subEntry.getValue());
                            if (subEntry.getKey().equals("hours")) {
                                Log.i(TAG, subEntry.getValue());
                                bills.setHours(subEntry.getValue());
                            } else if (subEntry.getKey().equals("rating")) {
                                Log.i(TAG, subEntry.getValue());
                                bills.setRating(subEntry.getValue());
                            } else if (subEntry.getKey().equals("price")) {
                                Log.i(TAG, subEntry.getValue());
                                bills.setTotalPrice(subEntry.getValue());
                            }
                        }
                        billsArrayList.add(bills);
                    }
                        printOnListView(billsArrayList);
                } else {
                    Toast.makeText(getApplicationContext(), "Sorry, Invalid Username", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }

        });
    }

    private void printOnListView(ArrayList<Bills> billsArrayList) {
        for(Bills bill : billsArrayList) {
            billList.add("Parked Hours : " + bill.getHours() + "\n" + "Price : " + bill.getTotalPrice() + "\n" + "Rating : " + bill.getRating());
        }
    }
}
