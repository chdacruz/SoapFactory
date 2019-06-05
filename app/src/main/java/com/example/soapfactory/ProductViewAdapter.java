package com.example.soapfactory;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

//Later change this to a fragment
public class ProductViewAdapter extends RecyclerView.Adapter<ProductViewAdapter.ViewHolder>{

    Context mContext;
    //List<Product> mName;
    //List<Product> mDescription;
    //List<Product> mData;
    //List<Product> mDataFiltered;
    private ArrayList<String> mNames;
    private ArrayList<String> mDescriptions;

    public ProductViewAdapter(Context context, ArrayList<String> name, ArrayList<String> description) {
        this.mContext = context;
        this.mNames = name;
        this.mDescriptions = description;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_product, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewAdapter.ViewHolder holder, final int position) {
        //productViewHolder.prodName.setText(mDataFiltered.get(position).getName());
        //productViewHolder.prodDescription.setText(mDataFiltered.get(position).getDescription());
        holder.prodName.setText(mNames.get(position));
        holder.prodDescription.setText(mDescriptions.get(position));

        holder.parentLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(mContext, mNames.get(position), Toast.LENGTH_SHORT).show();
            }

        });
    }

    //Defines the size of the recycleview list
    @Override
    public int getItemCount() {
        return mNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView prodName, prodDescription;
        //RelativeLayout parentLayout;
        LinearLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            prodName = itemView.findViewById(R.id.rv_prodName);
            prodDescription = itemView.findViewById(R.id.rv_prodDescription);
            parentLayout = itemView.findViewById(R.id.rv_containerLayout);
        }
    }



}
