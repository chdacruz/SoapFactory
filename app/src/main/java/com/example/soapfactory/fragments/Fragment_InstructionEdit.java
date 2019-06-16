package com.example.soapfactory.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.soapfactory.R;
import com.example.soapfactory.models.Instruction;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Fragment_InstructionEdit extends Fragment {

    EditText txtTitle, txtDescription;
    Button btnRegister;

    DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_instructions, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //super.onViewCreated(view, savedInstanceState);

        txtTitle = view.findViewById(R.id.txtInstruction_Title);
        txtDescription  = view.findViewById(R.id.txtInstruction_Description);
        btnRegister = view.findViewById(R.id.btn_RegisterInstruction);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = txtTitle.getText().toString();
                String description = txtDescription.getText().toString();

                Instruction instruction = new Instruction(title, description);

                databaseReference = FirebaseDatabase.getInstance().getReference();

                databaseReference.child("instructions").child(title).setValue(instruction).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        showMessage("Instructions registered");
                        clearFields();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showMessage(e.getMessage());

                    }
                });

            }
        });
    }

    private void clearFields(){
        txtTitle.setText("");
        txtDescription.setText("");
    }

    private void showMessage(String message) {

        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();

    }
}
