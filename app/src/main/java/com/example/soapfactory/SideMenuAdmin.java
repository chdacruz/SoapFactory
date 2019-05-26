package com.example.soapfactory;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class SideMenuAdmin extends AppCompatActivity implements AdapterView.OnItemSelectedListener, NavigationView.OnNavigationItemSelectedListener {

    //Sign Out Button
    //private Button btnSignOut;

    //Drawer menu
    private DrawerLayout drawer;
    TextView userEmail;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sidemenu_admin);


        //Side bar code
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.sideMenuAdmin);

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
            navigationView.setCheckedItem(R.id.nav_home_admin);
        }



        //Put current user's e-mail on side bar menu
        //For some reason the app doesn't load after login if this code is uncommented
        /*FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {
            TextView emailTxt = findViewById(R.id.emailTextView);
            String email = user.getEmail();
            emailTxt.setText("" + email);
        }*/

        //NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

       /* final FirebaseUser mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        navigationView.setNavigationItemSelectedListener(this);

        View navHeaderView = navigationView.getHeaderView(0);

        userEmail = (TextView) navHeaderView.findViewById(R.id.emailTextView);

        FirebaseDatabase.getInstance().getReference().child(mFirebaseUser.getEmail().replace(".", ","))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if( dataSnapshot.getValue() != null){
                            //Users users = dataSnapshot.getValue(Users.class);
                            PostData postData = dataSnapshot.getValue(PostData.class);

                            //Change to e-mail later
                            //userEmail.setText(postData.getName());
                            //userEmail.setText(mFirebaseUser.getEmail().toString());
                            userEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });*/



        /* Remove everything until corrected
        btnSignOut = findViewById(R.id.btnSign_Out);
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Logout
                AuthUI.getInstance()
                        .signOut(SideMenuAdmin.this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                toMainActivity(true);
                            }
                        });
            }
        });

        End removing everything*/


    }



    //Implementing method to call fragments
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_home_admin:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeFragmentAdmin()).commit();
                break;
            case R.id.nav_profile_admin:
                //Here the profile fragment is the same used for user
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProfileFragment()).commit();
                break;
            case R.id.nav_prod_registration_admin:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Prod_RegistrationFragment()).commit();
                break;
            case R.id.nav_fab_instructions_admin:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Fab_InstructionsFragment()).commit();
                break;

            case R.id.nav_sign_out_admin:
                signOut();
                break;

            /*case R.id.nav_share:
                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_send:
                Toast.makeText(this, "Send", Toast.LENGTH_SHORT).show();
                break;*/
        }

        //Close Drawer
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    private void signOut(){

        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(SideMenuAdmin.this, MainActivity.class));
                        finish();
                    }
                });
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

    private void toMainActivity(boolean signed){
        if(true){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Not logged. Weird error.", Toast.LENGTH_SHORT).show();
        }
    }
}
