package com.example.soapfactory;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

//Change this later to a fragment
public class Activity_ProductView extends AppCompatActivity {

    //vars
    //private ArrayList<Product> mData = new ArrayList<>();
    //private ArrayList<Product> mDescriptions = new ArrayList<>();
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mDescriptions = new ArrayList<>();

    //RecyclerView
    //RecyclerView productRecyclerview;
    //ProductViewAdapter productAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product);

        //init view
        //productRecyclerview = findViewById(R.id.rv_screen);

        //adapter init and setup
        //productAdapter = new ProductViewAdapter(this, mNames, mDescriptions);
        //productRecyclerview.setAdapter(productAdapter);
        //productRecyclerview.setLayoutManager(new LinearLayoutManager(this));


        initValues();

    }

   private void initValues() {

        //mData.add(new Product("1", "asdfkjahsdfkasdioufasdf"));
        //mData.add(new Product("2", "opppppppppppp"));
        //mData.add(new Product("3", "aaaaaaaaaaaaaaaaaa"));

        mNames.add("1");
        mDescriptions.add("asdfhasodifuhasdf");
        mNames.add("2");
        mDescriptions.add("adsflkasudfh");
        mNames.add("3");
        mDescriptions.add("zsdasffffffffffffffffff");

        initRecycleView();
    }

    private void initRecycleView() {
        RecyclerView recyclerView = findViewById(R.id.rv_screen);
        ProductViewAdapter adapter = new ProductViewAdapter(this, mNames, mDescriptions);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}
