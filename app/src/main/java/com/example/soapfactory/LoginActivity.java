package com.example.soapfactory;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private static final int GOOGLE_SIGN_CODE = 666; // ANy number you'd like
    private static final int FACEBOOK_SIGN_CODE = 667;
    private Button btnSignInEmail, btnSignFacebook;
    private LoginButton btnSignFacebook_Widget;
    private ImageButton btnSignGoogle;
    ProgressBar emailProgressBar;

    //For facebook login
    CallbackManager mCallbackManager;

    FirebaseAuth mAuth;

    //For login with e-mail
    TextView txtUserEmail, txtUserPassword;

    //For register function
    TextView txtRegister;

    //Google sign variables
    GoogleSignInOptions gso;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Linking variables to TextViews
        txtUserEmail = findViewById(R.id.txtUsername_Login);
        txtUserPassword = findViewById(R.id.txtPassword_Login);

        //Progress bar
        emailProgressBar = findViewById(R.id.email_login_progress_bar);
        emailProgressBar.setVisibility(View.INVISIBLE);

        //Firebase auth
        mAuth = FirebaseAuth.getInstance();

        //Email sign-in();
        btnSignInEmail = findViewById(R.id.btnSign_In);
        btnSignInEmail.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                emailProgressBar.setVisibility(View.VISIBLE);
                btnSignInEmail.setVisibility(View.INVISIBLE);
                btnSignInEmail.setEnabled(true);

                final String email = txtUserEmail.getText().toString();
                final String password = txtUserPassword.getText().toString();

                if(email.isEmpty() || password.isEmpty()){
                    showMessage("Invalid e-mail or password. Please verify all fields");
                    btnSignInEmail.setVisibility(View.VISIBLE);
                    emailProgressBar.setVisibility(View.INVISIBLE);
                }

                else{
                    emailSign(email, password);
                }
            }

        });

        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();
        btnSignFacebook_Widget = findViewById(R.id.btnSign_In_Facebook_Widget);
        btnSignFacebook_Widget.setVisibility(View.INVISIBLE);
        btnSignFacebook = findViewById(R.id.btnSign_In_Facebook);

        btnSignFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSignFacebook_Widget.setReadPermissions("email", "public_profile");
                btnSignFacebook_Widget.performClick();
                btnSignFacebook_Widget.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        showMessage("facebook:onSuccess:" + loginResult);
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        showMessage("facebook:onCancel");
                        // ...
                    }

                    @Override
                    public void onError(FacebookException error) {
                        showMessage("facebook:onError" + error);
                        // ...
                    }
                });
            }
        });


        //Google sign code
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

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
                Intent intentRegister = new Intent(LoginActivity.this, RegisterUser.class);
                startActivity(intentRegister);
            }
        });

    }

    private void handleFacebookAccessToken(AccessToken token) {
        showMessage("handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            showMessage("signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            userStartup();

                        } else {
                            // If sign in fails, display a message to the user.
                            showMessage("signInWithCredential:failure" + task.getException());
                            //Toast.makeText(FacebookLoginActivity.this, "Authentication failed.",
                                    //Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }


    //Not working properly. onActivityResult() method is needed
    private void googleSign() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, GOOGLE_SIGN_CODE);
        finish();
    }


    private void emailSign(String email, String password){

        //New version
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    emailProgressBar.setVisibility(View.INVISIBLE);
                    btnSignInEmail.setVisibility(View.VISIBLE);
                    admStartup();

                }
                else {
                    showMessage(task.getException().getMessage());
                    btnSignInEmail.setVisibility(View.VISIBLE);
                    emailProgressBar.setVisibility(View.INVISIBLE);
                }

            }
        });

    }

    private void showMessage(String text) {

        Toast.makeText(getApplicationContext(),text,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == GOOGLE_SIGN_CODE) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleGoogleSignInResult(task);
        }
    }

    private void handleGoogleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            startActivity(new Intent(LoginActivity.this, SideMenuUser.class));
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Google Sign In Error", "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(LoginActivity.this, "Failed", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected void onStart() {

        FirebaseUser user = mAuth.getCurrentUser();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        //Check for existing firebase sign-in
        if(user != null) {
            //user is already connected  so we need to redirect him to home page
            admStartup();

        }
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        else if(account != null) {
            startActivity(new Intent(LoginActivity.this, SideMenuUser.class));
        }

        super.onStart();

    }

    private void admStartup(){
        Intent intentAdmin = new Intent(this, SideMenuAdmin.class);
        startActivity(intentAdmin);
        finish();
    }

    private void userStartup(){
        Intent intentUser = new Intent(this, SideMenuUser.class);
        startActivity(intentUser);
        finish();
    }


}























