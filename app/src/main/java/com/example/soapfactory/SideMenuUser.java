package com.example.soapfactory;

import android.content.Intent;
import android.net.Uri;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SideMenuUser extends AppCompatActivity implements AdapterView.OnItemSelectedListener, NavigationView.OnNavigationItemSelectedListener {

    //Drawer menu
    private DrawerLayout drawer;

    //
    TextView nav_userName;
    TextView nav_userEmail;
    ImageView nav_userPhoto;

    //Google
    GoogleSignInClient mGoogleSignInClient;
    GoogleSignInOptions gso;
    GoogleSignInAccount googleAcc;

    //Firebase
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sidemenu_user);

        //Creates reference to Navigation View to enable click on events
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Get header view to enable findViewById()
        View hView =  navigationView.getHeaderView(0);

        nav_userName = hView.findViewById(R.id.nav_user_name);
        nav_userEmail = hView.findViewById(R.id.nav_user_email);
        nav_userPhoto = hView.findViewById(R.id.nav_user_photo);

        //Side bar code
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.sideMenuUser);

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

        //********************************* GOOGLE ********************
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        googleAcc = GoogleSignIn.getLastSignedInAccount(SideMenuUser.this);
        googleSetNavText(googleAcc);
        //********************************* END GOOGLE ********************


        //********************************* FACEBOOK / E-MAIL (FIREBASE) ********************
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        firebaseSetNavText(firebaseUser);


        //********************************* END FACEBOOK ********************


    }

    public void firebaseSetNavText(FirebaseUser fUser) {

        if (fUser != null) {
            String pName = fUser.getDisplayName();
            String pEmail = fUser.getEmail();
            Uri pPhoto = fUser.getPhotoUrl();

            nav_userName.setText(pName);
            nav_userEmail.setText(pEmail);
            //Glide.with(this).load(pPhoto).into(nav_userPhoto);
            Glide.with(this)
                    .load(pPhoto)
                    .centerCrop()
                    .into(nav_userPhoto);
        }
    }

    public void googleSetNavText(GoogleSignInAccount googleAcc2){

        //googleAcc2 = GoogleSignIn.getLastSignedInAccount(SideMenuUser.this);

        if(googleAcc2 != null){
            String pName = googleAcc2.getDisplayName();
            String pEmail = googleAcc2.getEmail();
            Uri pPhoto = googleAcc2.getPhotoUrl();

            nav_userName.setText(pName);
            nav_userEmail.setText(pEmail);
            //Glide.with(this).load(pPhoto).into(nav_userPhoto);
            Glide.with(this)
                    .load(pPhoto)
                    .centerCrop()
                    .into(nav_userPhoto);

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

            case R.id.nav_product_view_user:
                Intent intentProduct = new Intent(this, Activity_ProductView.class);
                startActivity(intentProduct);
                finish();
                break;

            case R.id.nav_share_user:
                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_send_user:
                Toast.makeText(this, "Send", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_sign_out_user:
                signOut();
                break;
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
                        startActivity(new Intent(SideMenuUser.this, LoginActivity.class));
                        finish();
                    }
                });

        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        showMessage("Signed out");
                        startActivity(new Intent(SideMenuUser.this, LoginActivity.class));
                        finish();
                    }
                });
    }

    private void showMessage(String text) {

        Toast.makeText(getApplicationContext(),text,Toast.LENGTH_LONG).show();
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
