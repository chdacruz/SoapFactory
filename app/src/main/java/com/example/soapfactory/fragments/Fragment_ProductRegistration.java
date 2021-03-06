package com.example.soapfactory.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.soapfactory.models.Product;
import com.example.soapfactory.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Fragment_ProductRegistration extends Fragment {

    //Variables to save the data to the database
    EditText edt_prodName, edt_prodPrice, edt_description;
    Spinner spnProdType;
    Button btn_insert;

    //Spinner adapter
    ArrayAdapter<CharSequence> adapter;

    DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //View v = inflater.inflate(R.layout.fragment_prod_registration, container, false);
        View v = inflater.inflate(R.layout.fragment_prod_registration, container, false);

        //return inflater.inflate(R.layout.fragment_prod_registration, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);

        edt_prodName = view.findViewById(R.id.frag_ProdName);
        edt_prodPrice = view.findViewById(R.id.frag_ProdPrice);
        spnProdType = view.findViewById(R.id.frag_spn_ProdType);
        edt_description = view.findViewById(R.id.frag_ProdDescription);
        btn_insert = view.findViewById(R.id.frag_btn_RegisterProduct);

        adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.prod_type,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnProdType.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String prodName = edt_prodName.getText().toString();
                String prodPrice = edt_prodPrice.getText().toString();
                String prodType = spnProdType.getSelectedItem().toString();
                String prodDescription = edt_description.getText().toString();

                if(prodName.isEmpty() || prodPrice.isEmpty() || prodDescription.isEmpty() || prodType.isEmpty()){
                    showMessage("Please verify all fields");
                }

                else {
                    Product product = new Product(prodName, prodPrice, prodType, prodDescription);

                    databaseReference = FirebaseDatabase.getInstance().getReference();

                    databaseReference.child("products").child(prodName).setValue(product).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            showMessage("Product Registered");
                            clearFields();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            showMessage(e.getMessage());

                        }
                    });
                }
            }
        });
    }

    private void clearFields(){
        edt_prodName.setText("");
        edt_prodPrice.setText("");
        edt_description.setText("");
        spnProdType.setSelection(0);
    }

    private void showMessage(String message) {

        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();

    }


}
