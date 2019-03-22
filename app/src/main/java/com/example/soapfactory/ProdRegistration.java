package com.example.soapfactory;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

public class ProdRegistration extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //Sign Out Button
    private Button btnSignOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prod_registration);

        btnSignOut = findViewById(R.id.btnSign_Out);
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Logout
                AuthUI.getInstance()
                        .signOut(ProdRegistration.this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                toMainActivity(true);
                            }
                        });
            }
        });


        //Spinner Soap
        Spinner spnSoap = findViewById(R.id.spnSoapType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.soap_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnSoap.setAdapter(adapter);
        spnSoap.setOnItemSelectedListener(this);

        //Spinner Fat
        Spinner spnFat = findViewById(R.id.spnFatType);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.fat_types, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnFat.setAdapter(adapter1);
        spnFat.setOnItemSelectedListener(this);

        //Scent
        Spinner spnScent = findViewById(R.id.spnScent);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.scent, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnScent.setAdapter(adapter2);
        spnScent.setOnItemSelectedListener(this);


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
