package com.example.soapfactory;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    DatabaseReference db;

    //EditText nameField = findViewById(R.id.edtxtName_Register);
    EditText emailField = findViewById(R.id.edtxtEmail_Register);
    EditText passwordField = findViewById(R.id.edtxtPassword_Register);
    Button btnRegister = findViewById(R.id.btnRegisterEmail);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_email);

        firebaseAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnRegister.setEnabled(true);
                String email = emailField.getText().toString();
                String password = passwordField.getText().toString();

                createAccount(email, password);

            }
        });
    }


    private void createAccount(final String email, final String password){
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(RegisterUser.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            //db = FirebaseDatabase.getInstance().getReference();

                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            String id = firebaseUser.getUid();
                            User user = new User(id, email);
                            UserDAO userDAO = new UserDAO();
                            userDAO.create(id, user);

                            //db = FirebaseDatabase.getInstance().getReference();
                            //db.child("users").child(id).setValue(user);

                            Toast.makeText(getApplicationContext(), "Users successfully registered", Toast.LENGTH_SHORT).show();

                            //Then go to login activity
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));

                        }

                        else{
                            Toast.makeText(getApplicationContext(), "An error occurred during user registration", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }






}
