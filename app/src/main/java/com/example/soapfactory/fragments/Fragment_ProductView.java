package com.example.soapfactory.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.soapfactory.adapters.ProductViewAdapter;
import com.example.soapfactory.R;
import com.example.soapfactory.models.Product;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

//Change this later to a fragment
public class Fragment_ProductView extends Fragment {

    //vars
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mDescriptions = new ArrayList<>();

    private ArrayList<Product> mProducts = new ArrayList<>();

    public Fragment_ProductView(){
        //Required empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_list_product, container, false);

        initValues(view);

        return view;
    }



    private void initValues(View view) {


        /*mNames.add("1");
        mDescriptions.add("asdfhasodifuhasdf");
        mNames.add("2");
        mDescriptions.add("adsflkasudfh");
        mNames.add("3");
        mDescriptions.add("zsdasffffffffffffffffff");*/

        mProducts.add(new Product("Name1", "00000", "a", "asdfasdfsadf"));
        mProducts.add(new Product("Name2", "22222", "b", "asdfasdfsadf"));
        mProducts.add(new Product("Name3", "33333", "c", "asdfasdfsadf"));

        initRecycleView(view);
    }

    private void initRecycleView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.rv_screen);
        //ProductViewAdapter adapter = new ProductViewAdapter(getActivity(), mNames, mDescriptions);
        ProductViewAdapter adapter = new ProductViewAdapter(getActivity(), mProducts);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

}
