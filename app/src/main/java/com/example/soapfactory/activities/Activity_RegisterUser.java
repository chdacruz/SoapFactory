package com.example.soapfactory.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.soapfactory.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Activity_RegisterUser extends AppCompatActivity {

    ImageButton imgUserPhoto;
    static int PReqCode = 1 ;
    static int REQUESCODE = 2;
    Uri pickedImgUri ;

    private EditText userEmail,userPassword,userPassword2,userName;
    private Button regBtn;

    private FirebaseAuth mAuth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        userEmail = findViewById(R.id.editText_registerEmail);
        userName = findViewById(R.id.editText_registerName);
        userPassword = findViewById(R.id.editText_registerPassword);
        userPassword2 = findViewById(R.id.editText_registerConfirmPassword);
        regBtn = findViewById(R.id.btn_Register);

        mAuth = FirebaseAuth.getInstance();

        regBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                regBtn.setVisibility(View.INVISIBLE);

                final String email = userEmail.getText().toString();
                final String name = userName.getText().toString();
                final String password = userPassword.getText().toString();
                final String confirmPassword = userPassword2.getText().toString();

                if(!validateFields(email, name, password, confirmPassword)){
                    regBtn.setVisibility(View.VISIBLE);
                    return;
                }

                else {
                    createUserAccount(email,name,password);
                }

                /*if( email.isEmpty() || name.isEmpty() || password.isEmpty()  || !password.equals(confirmPassword)) {
                    // something goes wrong : all fields must be filled
                    // we need to display an error message
                    showMessage("Please Verify all fields") ;
                    regBtn.setVisibility(View.VISIBLE);

                }
                else {
                    // everything is ok and all fields are filled now we can start creating user account
                    // CreateUserAccount method will try to create the user if the email is valid

                    createUserAccount(email,name,password);
                }*/



            }
        });

        imgUserPhoto = findViewById(R.id.img_regUserPhoto) ;

        imgUserPhoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                checkAndRequestForPermission();
                openGallery();
                showMessage("Open gallery method called");
            }
        });

    }

    private boolean validateFields(String email, String name, String passwd1, String passwd2){
        boolean isValid = true;

        if(email.isEmpty() || name.isEmpty() || passwd1.isEmpty()  || !passwd1.equals(passwd2)){
            showMessage("Please Verify all fields");
            isValid = false;
        }

        if(passwd1.length() < 6 || passwd2.length() < 6){
            showMessage("Password must contain at least 6 characters");
            isValid = false;
        }

        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            showMessage("Invalid e-mail");
            isValid = false;
        }

        return isValid;
    }



    private void checkAndRequestForPermission() {
        if (ContextCompat.checkSelfPermission(Activity_RegisterUser.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(Activity_RegisterUser.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                Toast.makeText(Activity_RegisterUser.this,"Please accept for required permission",Toast.LENGTH_SHORT).show();

            }

            else
            {
                ActivityCompat.requestPermissions(Activity_RegisterUser.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PReqCode);
            }

        }
        else
            openGallery();

    }

    public void openGallery(){

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,REQUESCODE);

    }


    public void createUserAccount(String email, final String name, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // user account created successfully
                            showMessage("Account created");
                            // after we created user account we need to update his profile picture and name
                            updateUserInfo( name ,pickedImgUri,mAuth.getCurrentUser());

                        }
                        else
                        {
                            // account creation failed
                            showMessage("account creation failed" + task.getException().getMessage());
                            regBtn.setVisibility(View.VISIBLE);
                        }

                    }
                });


    }

    // update user photo and name
    public void updateUserInfo(final String name, Uri pickedImgUri, final FirebaseUser currentUser){

        // first we need to upload user photo to firebase storage and get url

        StorageReference mStorage = FirebaseStorage.getInstance().getReference().child("users_photos");
        final StorageReference imageFilePath = mStorage.child(pickedImgUri.getLastPathSegment());
        imageFilePath.putFile(pickedImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                // image uploaded succesfully
                // now we can get our image url
                imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        // uri contain user image url
                        UserProfileChangeRequest profleUpdate = new UserProfileChangeRequest.Builder()
                                .setDisplayName(name)
                                .setPhotoUri(uri)
                                .build();

                        currentUser.updateProfile(profleUpdate)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()) {
                                            // user info updated successfully
                                            showMessage("Register Complete");
                                            updateUI();
                                        }

                                    }
                                });
                    }
                });
            }
        });



    }

    private void updateUI() {
        Intent home = new Intent(getApplicationContext(), Activity_Login.class);
        startActivity(home);
        finish();
    }

    // simple method to show toast message
    private void showMessage(String message) {

        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUESCODE && data != null ) {

            // the user has successfully picked an image
            // we need to save its reference to a Uri variable
            pickedImgUri = data.getData() ;
            imgUserPhoto.setImageURI(pickedImgUri);
        }
    }
}
