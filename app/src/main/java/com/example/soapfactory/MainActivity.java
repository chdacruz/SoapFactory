package com.example.soapfactory;

import android.content.Intent;
import android.support.annotation.NonNull;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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

        //Firebase auth
        mAuth = FirebaseAuth.getInstance();

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

                final String email = txtUserEmail.getText().toString();
                final String password = txtUserPassword.getText().toString();

                emailSign(email, password);
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

    }


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
    private void emailSign(String email, String password){

        //New version
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    btnSignInEmail.setVisibility(View.VISIBLE);
                    updateUI();

                }
                else {
                    showMessage(task.getException().getMessage());
                    btnSignInEmail.setVisibility(View.VISIBLE);
                }

            }
        });


        /****************   Old version
        String userEmail = txtUserEmail.getText().toString();
        String userPassword = txtUserPassword.getText().toString();

        Users users = new Users(userEmail, userPassword);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        //Insert this here
        databaseReference = FirebaseDatabase.getInstance().getReference();

        //**************** May 22
        //Should get the e-mail and compare with database. If the user's email is admin, login with admin.
        //Else, login with common user privileges

        //Should save the user to the database, which is not happening
        databaseReference.child("users").setValue(users);


        Intent intentAdmin = new Intent(this, SideMenuAdmin.class);
        startActivity(intentAdmin);*/


    }

    private void updateUI() {

        Intent intent = new Intent(this, SideMenuAdmin.class);
        startActivity(intent);
        finish();

    }

    private void showMessage(String text) {

        Toast.makeText(getApplicationContext(),text,Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();

        if(user != null) {
            //user is already connected  so we need to redirect him to home page
            updateUI();

        }



    }

    private void admStartup(){
        Intent intentAdmin = new Intent(this, SideMenuAdmin.class);
        startActivity(intentAdmin);
    }

    private void userStartup(){
        Intent intentUser = new Intent(this, SideMenuUser.class);
        startActivity(intentUser);
    }


    /******* Is not needed anymore
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

        ///*****************************************************************************************
    }*/
}























