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
import android.widget.EditText;

import com.example.soapfactory.adapters.ProductViewAdapter;
import com.example.soapfactory.R;
import com.example.soapfactory.models.Product;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

//Change this later to a fragment
public class Fragment_ProductView extends Fragment {

    //vars
    //private ArrayList<String> mNames = new ArrayList<>();
    //private ArrayList<String> mDescriptions = new ArrayList<>();


    RecyclerView recyclerView;
    //ProductViewAdapter adapter;
    FirebaseRecyclerAdapter adapter;

    //FirebaseDatabase mDatabase;
    DatabaseReference mDatabase;

    //Search product
    EditText search_product;
    ProductViewAdapter productViewAdapter;
    private ArrayList<Product> mProducts;

    public Fragment_ProductView(){
        //Required empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list_product, container, false);

        //initValues(view);
        //initRecycleView(view);

        /**********
         *
         */
        recyclerView = view.findViewById(R.id.rv_productListScreen);

        //To filter data
        /*mProducts = new ArrayList<>();
        productViewAdapter = new ProductViewAdapter(getActivity(), mProducts);*/

        mDatabase = FirebaseDatabase.getInstance().getReference().child("products");
        mDatabase.keepSynced(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("products").orderByValue();

        FirebaseRecyclerOptions<Product> options = new FirebaseRecyclerOptions.Builder<Product>()
                .setQuery(query, Product.class).build();

        adapter = new FirebaseRecyclerAdapter<Product, ProductViewAdapter.ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewAdapter.ViewHolder holder, int position, @NonNull Product model) {
                holder.prodDescription.setText(model.getDescription());
                holder.prodName.setText(model.getName());
                holder.prodPrice.setText(model.getPrice());
                holder.prodType.setText(model.getType());
            }

            @NonNull
            @Override
            public ProductViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view1 = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_product, viewGroup, false);
                return new ProductViewAdapter.ViewHolder(view1);
            }
        };

        recyclerView.setAdapter(adapter);
        /**********
         *
         */

        //Search product
        /*
        search_product = view.findViewById(R.id.search_product);
        search_product.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                productViewAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(adapter != null){
            adapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(adapter != null){
            adapter.stopListening();
        }
    }
}
