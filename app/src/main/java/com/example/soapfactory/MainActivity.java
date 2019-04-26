package com.example.soapfactory;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    //FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Linking variables to TextViews
        txtUserEmail = findViewById(R.id.txtUsername);
        txtUserPassword = findViewById(R.id.txtPassword);

        //Init providers
        providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.FacebookBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build()
        );

        providerFacebook = Arrays.asList( new AuthUI.IdpConfig.FacebookBuilder().build() );
        providerGoogle = Arrays.asList(new AuthUI.IdpConfig.GoogleBuilder().build() );

        //showSignInOptions();
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


        //Display e-mail
        //loadUserInformation();
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

    private void emailSign(){
        String userEmail = txtUserEmail.getText().toString();
        String userPassword = txtUserPassword.getText().toString();

        Users users = new Users(userEmail, userPassword);

        databaseReference.child("users").setValue(users);
    }

    /*private void showSignInOptions() {
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setTheme(R.style.AppTheme)
                .build(), MY_REQUEST_CODE


        );
    }*/


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_OK){
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if(resultCode == RESULT_OK){
                //Get user
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                //Show e-mail on Toast
                Toast.makeText(this, ""+user.getEmail(), Toast.LENGTH_SHORT).show();
                //Set Button Signout
                    //btnSignOut.setEnabled(true);

            }
            else{
                Toast.makeText(this, ""+response.getError().getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        //Open next activity
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }
}
