package com.example.soapfactory;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

public class SideMenuUser extends AppCompatActivity implements AdapterView.OnItemSelectedListener, NavigationView.OnNavigationItemSelectedListener {

    //Drawer menu
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sidemenu_user);


        //Side bar code
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.sideMenuUser);

        //Creates reference to Navigation View to enable click on events
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //If the fragment is destroyed (for instance when the application is rotated)
        if(savedInstanceState == null) {
            //Open a fragment on start
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragmentAdmin()).commit();
            navigationView.setCheckedItem(R.id.nav_home_user);
        }


    }







    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_home_user:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeFragmentUser()).commit();
                break;

            case R.id.nav_profile_user:
                //Here the profile fragment is the same used for admin
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProfileFragment()).commit();
                break;

            case R.id.nav_fab_instructions_user:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Fab_InstructionsFragment()).commit();
                break;

            case R.id.nav_share_user:
                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_send_user:
                Toast.makeText(this, "Send", Toast.LENGTH_SHORT).show();
                break;
        }

        //Close Drawer
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    //Side bar code (in case the back button is pressed)
    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}