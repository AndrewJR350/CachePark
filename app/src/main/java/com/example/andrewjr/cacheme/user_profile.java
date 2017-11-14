package com.example.andrewjr.cacheme;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class user_profile extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private String TAG = "cacheMe";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getFragmentManager();




        if (id == R.id.viewBills) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new view_bills_fragment()).commit();
//            Intent intent = new Intent(user_profile.this,view_bills.class);
//            startActivity(intent);
//            Toast.makeText(getApplicationContext(), "Hello, Here are your Bills", Toast.LENGTH_LONG).show();
        } else if (id == R.id.account) {
            Intent intent = new Intent(user_profile.this, user_profile.class);
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "Hi Welcome !!! ", Toast.LENGTH_LONG).show();


        } else if (id == R.id.getMap) {
            Intent intent = new Intent(user_profile.this, available_slots.class);
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "Changing to Map", Toast.LENGTH_LONG).show();

        } else if (id == R.id.signOut) {
            Intent intent = new Intent(user_profile.this, login.class);
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "Successfully Sign out", Toast.LENGTH_LONG).show();

        }else if (id == R.id.slotsRegister){
            fragmentManager.beginTransaction().replace(R.id.content_frame, new register_slot_fragment()).commit();
//            Intent intent = new Intent(user_profile.this, register_slot.class);
//            startActivity(intent);
//            startActivity(intent);
//            Toast.makeText(getApplicationContext(), "Register Your Slots", Toast.LENGTH_LONG).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
