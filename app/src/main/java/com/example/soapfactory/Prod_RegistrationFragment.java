package com.example.soapfactory;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Prod_RegistrationFragment extends Fragment {

    //Variables to save the data to the database
    //EditText edt_prodName, edt_prodPrice, edt_description;
    //Spinner spnProdType;
    //ImageButton btn_insert;


    //Firebase

    //FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    //@Nullable
    //    @Override
    //    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //View v = inflater.inflate(R.layout.fragment_prod_registration, container, false);
        View v = inflater.inflate(R.layout.fragment_prod_registration, container, false);

        /****
        //edt_prodName = v.findViewById(R.id.editTxt_ProdName);
        edt_prodName = v.findViewById(R.id.frag_ProdName);
        //edt_prodPrice = v.findViewById(R.id.editTxt_ProdPrice);
        edt_prodPrice = v.findViewById(R.id.frag_ProdPrice);
        //spnProdType = v.findViewById(R.id.spinner_ProdType);
        //spnProdType = v.findViewById(R.id.frag_spn_ProdType);
        //edt_description = v.findViewById(R.id.editTxt_Description);
        edt_description = v.findViewById(R.id.frag_ProdDescription);
        //btn_insert = v.findViewById(R.id.btnInsert);
        btn_insert = v.findViewById(R.id.frag_btn_RegisterProduct);



        Spinner Code
        //Code to initiate the spinner
        String [] prodTypes = {"Oil", "Fat", "Scent"};
        //spnProdType = v.findViewById(R.id.spinner_ProdType);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, prodTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnProdType.setAdapter(adapter);


        //Firebase

        //firebaseDatabase = FirebaseDatabase.getInstance();
        //On video: databaseReference = firebaseDatabase.getReference("EDMT_REFERENCE");
        //Where's the string name from?
        //databaseReference = firebaseDatabase.getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
            }
        });*/

        //return inflater.inflate(R.layout.fragment_prod_registration, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        EditText edt_prodName, edt_prodPrice, edt_description;
        Spinner spnProdType;
        Button btn_insert;

        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
        //edt_prodName = v.findViewById(R.id.editTxt_ProdName);
        edt_prodName = view.findViewById(R.id.frag_ProdName);
        //edt_prodPrice = v.findViewById(R.id.editTxt_ProdPrice);
        edt_prodPrice = view.findViewById(R.id.frag_ProdPrice);
        //spnProdType = v.findViewById(R.id.spinner_ProdType);
        //spnProdType = v.findViewById(R.id.frag_spn_ProdType);
        //edt_description = v.findViewById(R.id.editTxt_Description);
        edt_description = view.findViewById(R.id.frag_ProdDescription);
        //btn_insert = v.findViewById(R.id.btnInsert);
        btn_insert = view.findViewById(R.id.frag_btn_RegisterProduct);
    }


   /*private void insertData() {
        String prodName = edt_prodName.getText().toString();
        String prodPrice = edt_prodPrice.getText().toString();
        String prodType = spnProdType.getSelectedItem().toString();
        String prodDescription = edt_description.getText().toString();

        PostData postData = new PostData(prodName, prodPrice, prodType, prodDescription);

        //Use this method to create unique id
        //databaseReference.push()
                //.setValue(postData);
       //databaseReference.setValue(postData);
       databaseReference.child("products").child(prodName).setValue(postData);

       //Makes everything returns to blank
       edt_prodName.setText("");
       edt_prodPrice.setText("");
       edt_description.setText("");

    }*/
}
