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

import com.example.soapfactory.adapters.InstructionViewAdapter;
import com.example.soapfactory.adapters.ProductViewAdapter;
import com.example.soapfactory.R;
import com.example.soapfactory.models.Instruction;
import com.example.soapfactory.models.Product;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class Fragment_InstructionView extends Fragment {

    RecyclerView recyclerView;
    FirebaseRecyclerAdapter adapter;

    DatabaseReference mDatabase;

    public Fragment_InstructionView(){
        //Required empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list_instruction, container, false);

        recyclerView = view.findViewById(R.id.rv_instructionListScreen);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("instructions");
        mDatabase.keepSynced(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("instructions").orderByValue();

        FirebaseRecyclerOptions<Instruction> options = new FirebaseRecyclerOptions.Builder<Instruction>()
                .setQuery(query, Instruction.class).build();

        adapter = new FirebaseRecyclerAdapter<Instruction, InstructionViewAdapter.ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull InstructionViewAdapter.ViewHolder holder, int position, @NonNull Instruction model) {
                holder.instructionTitle.setText(model.getTitle());
                holder.instructionContent.setText(model.getContent());
            }

            @NonNull
            @Override
            public InstructionViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view1 = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_instruction, viewGroup, false);
                return new InstructionViewAdapter.ViewHolder(view1);
            }
        };

        recyclerView.setAdapter(adapter);

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
