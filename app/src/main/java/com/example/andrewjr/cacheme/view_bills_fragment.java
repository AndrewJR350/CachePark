package com.example.andrewjr.cacheme;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * Created by keshav on 11/14/17.
 */

public class view_bills_fragment extends Fragment {

    View myView;
    private String TAG = "cacheMe";
    private ListView billsListView;
    private List<String> billList = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.activity_view_bills, container, false);
        return myView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        billsListView = (ListView) getActivity().findViewById(R.id.listview_bills);
        arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, billList);
        billsListView.setAdapter(arrayAdapter);
        getAllBill();

        super.onActivityCreated(savedInstanceState);
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
            billList.add("Parked Hours : " + bill.getHours()+ " Hour" + "\n" + "Price : " + bill.getTotalPrice()+ "$" + "\n" + "Rating : " + bill.getRating()+ "*");
        }
        arrayAdapter.notifyDataSetChanged();
    }
}
