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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

//Change this later to a fragment
public class Fragment_ProductView extends Fragment {

    //vars
    //private ArrayList<String> mNames = new ArrayList<>();
    //private ArrayList<String> mDescriptions = new ArrayList<>();
    private ArrayList<Product> mProducts = new ArrayList<>();

    RecyclerView recyclerView;
    //ProductViewAdapter adapter;
    FirebaseRecyclerAdapter adapter;

    //FirebaseDatabase mDatabase;
    DatabaseReference mDatabase;

    public Fragment_ProductView(){
        //Required empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_list_product, container, false);

        //initValues(view);
        //initRecycleView(view);


        /**********
         *
         */
        recyclerView = view.findViewById(R.id.rv_screen);
        //adapter = new ProductViewAdapter(getActivity(), mProducts);
        //recyclerView.setAdapter(adapter);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

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

    /*private void initValues(View view) {

        mProducts.add(new Product("Name1", "00000", "a", "asdfasdfsadf"));
        mProducts.add(new Product("Name2", "22222", "b", "asdfasdfsadf"));
        mProducts.add(new Product("Name3", "33333", "c", "asdfasdfsadf"));

        initRecycleView(view);
    }*/

    /*private void initRecycleView(View view) {
        recyclerView = view.findViewById(R.id.rv_screen);
        //adapter = new ProductViewAdapter(getActivity(), mProducts);
        //recyclerView.setAdapter(adapter);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mDatabase = FirebaseDatabase.getInstance().getReference().child("products");
        mDatabase.keepSynced(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("products").orderByValue();

        FirebaseRecyclerOptions<Product> options = new FirebaseRecyclerOptions.Builder<Product>()
                .setQuery(query, Product.class).build();

        FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<Product, ProductViewAdapter.ViewHolder>(options) {
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

    }*/



}
