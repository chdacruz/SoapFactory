package com.example.soapfactory;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final int MY_REQUEST_CODE = 666; // ANy number you'd like
    //private Button btnLogin;
    private Button btnSignInEmail;
    private ImageButton btnSignFacebook, btnSignGoogle;

    //Firebase login providers
    List<AuthUI.IdpConfig> providers;
    List<AuthUI.IdpConfig> providerFacebook;
    List<AuthUI.IdpConfig> providerGoogle;

    FirebaseAuth mAuth;

    //For login with e-mail
    TextView txtUserEmail, txtUserPassword;

    //For register function
    TextView txtRegister;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Check if this is necessary
        //databaseReference = FirebaseDatabase.getInstance().getReference();

        //Linking variables to TextViews
        txtUserEmail = findViewById(R.id.txtUsername_Login);
        txtUserPassword = findViewById(R.id.txtPassword_Login);

        //Init providers
        providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.FacebookBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build()
        );

        providerFacebook = Arrays.asList( new AuthUI.IdpConfig.FacebookBuilder().build() );
        providerGoogle = Arrays.asList(new AuthUI.IdpConfig.GoogleBuilder().build() );

        //Email sign-in();
        btnSignInEmail = findViewById(R.id.btnSign_In);
        btnSignInEmail.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                btnSignInEmail.setEnabled(true);
                emailSign();
                //showSignInOptions();
            }

        });

        btnSignFacebook = findViewById(R.id.btnSign_In_Facebook);
        btnSignFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSignFacebook.setEnabled(true);
                facebookSign();
            }

        });

        btnSignGoogle = findViewById(R.id.btnSign_In_Google);
        btnSignGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSignGoogle.setEnabled(true);
                googleSign();
            }
        });


        //Open register activity
        txtRegister = findViewById(R.id.txtRegister);
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRegister = new Intent(MainActivity.this, RegisterUser.class);
                startActivity(intentRegister);
            }
        });


        //Display e-mail
        //loadUserInformation();
    }

    //Register TextView
    /*
    For this to work you have to put on the xml design file
    android:onClick="onClick_txtRegister"
     */
    public void onClick_txtRegister(View view) {
        Intent intentRegister = new Intent(MainActivity.this, RegisterUser.class);
        startActivity(intentRegister);

    }

    //Display e-mail
    /*private void loadUserInformation() {
        FirebaseUser user = mAuth.getCurrentUser();
        if(user.getEmail() != null){
            userEmail = findViewById(R.id.emailTextView);
            userEmail.setText(user.getEmail());
        }

    }*/

    private void googleSign() {
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                .setAvailableProviders(providerGoogle)
                .build(), MY_REQUEST_CODE
        );
    }

    private void facebookSign() {
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                .setAvailableProviders(providerFacebook)
                .build(), MY_REQUEST_CODE
        );
    }

    //Only for login
    //Here's the problem. You're comparing the e-mail login with the firebase login, but you're not logging with firebase,
    //so it just get's the last logged user
    private void emailSign(){
        String userEmail = txtUserEmail.getText().toString();
        String userPassword = txtUserPassword.getText().toString();


        Users users = new Users(userEmail, userPassword);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        //Insert this here
        databaseReference = FirebaseDatabase.getInstance().getReference();

        //Should save the user to the database, which is not happening
        databaseReference.child("users").setValue(users);


        Intent intentAdmin = new Intent(this, SideMenuAdmin.class);
        startActivity(intentAdmin);


        /*if(userEmail.equals(user.getEmail())){
            Intent intentAdmin = new Intent(this, SideMenuAdmin.class);
            startActivity(intentAdmin);
        }

        else{
            Intent intentUser = new Intent(this, SideMenuUser.class);
            startActivity(intentUser);
        }*/



    }

    /*private void showSignInOptions() {
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setTheme(R.style.AppTheme)
                .build(), MY_REQUEST_CODE


        );
    }*/

    private void admStartup(){
        Intent intentAdmin = new Intent(this, SideMenuAdmin.class);
        startActivity(intentAdmin);
    }

    private void userStartup(){
        Intent intentUser = new Intent(this, SideMenuUser.class);
        startActivity(intentUser);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        if(requestCode == RESULT_OK){
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if(resultCode == RESULT_OK){
                //Get user
                //final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                //Show e-mail on Toast
                //Toast.makeText(this, ""+user.getEmail(), Toast.LENGTH_SHORT).show();
                //Set Button Signout
                    //btnSignOut.setEnabled(true);

            }
            else{
                Toast.makeText(this, ""+response.getError().getMessage(), Toast.LENGTH_SHORT).show();
            }
        }


        //Open different intents for different users
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(" Admins");

        // Read from the database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    if(ds.getValue(String.class).equals(user.getEmail())){
                        //Open activity
                        //startActivity(intentAdmin);
                        admStartup();
                    }
                    else{
                        //startActivity(intentUser);
                        userStartup();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Cruz" , "Failed to read value.", error.toException());
            }
        });

        //Open next activity
        //Intent intent = new Intent(this, SideMenuAdmin.class);
        //startActivity(intent);

        //Check if the user is an administrator


        //*****************************************************************************************
        /*
        String currentUser = getString(R.string.admin);

        if(currentUser.equals(user.getEmail())){
            Intent intentAdmin = new Intent(this, SideMenuAdmin.class);
            startActivity(intentAdmin);
        }

        else{
            Intent intentUser = new Intent(this, SideMenuUser.class);
            startActivity(intentUser);
        }

        *///*****************************************************************************************
    }

    /*public boolean isAdministrator(){

        boolean isAdmin = false;
        String email;

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(" Admins");

        // Read from the database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d("Cruz", "Value is: " + value);


                //Current user
                //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Cruz", "Failed to read value.", error.toException());
            }
        });

        return true;

    }*/
}























