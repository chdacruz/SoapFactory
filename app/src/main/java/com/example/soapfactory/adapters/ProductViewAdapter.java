package com.example.soapfactory.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.soapfactory.R;
import com.example.soapfactory.models.Product;

import java.util.ArrayList;
import java.util.List;

//Later change this to a fragment
public class ProductViewAdapter extends RecyclerView.Adapter<ProductViewAdapter.ViewHolder>{

    Context mContext;
    //AND THIS
    private ArrayList<String> mNames;
    private ArrayList<String> mDescriptions;
    //OR THIS
    private ArrayList<Product> products;


    public ProductViewAdapter(Context context, ArrayList<String> name, ArrayList<String> description) {
        this.mContext = context;
        this.mNames = name;
        this.mDescriptions = description;
    }

    public ProductViewAdapter(Context context, ArrayList<Product> products) {
        this.mContext = context;
        this.products = products;
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
        //holder.prodName.setText(mNames.get(position));
        //holder.prodDescription.setText(mDescriptions.get(position));
        holder.prodName.setText(products.get(position).getName());
        holder.prodDescription.setText(products.get(position).getDescription());
        holder.prodType.setText(products.get(position).getType());
        holder.prodPrice.setText(products.get(position).getPrice());

        holder.parentLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(mContext, mNames.get(position), Toast.LENGTH_SHORT).show();
                //Toast.makeText(mContext, (CharSequence) products.get(position), Toast.LENGTH_SHORT).show();
            }

        });
    }

    //Defines the size of the recycleview list
    @Override
    public int getItemCount() {
        //return mNames.size();
        return products.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView prodName, prodDescription, prodType, prodPrice;
        //RelativeLayout parentLayout;
        LinearLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            prodName = itemView.findViewById(R.id.rv_prodName);
            prodDescription = itemView.findViewById(R.id.rv_prodDescription);
            prodType = itemView.findViewById(R.id.rv_prodType);
            prodPrice = itemView.findViewById(R.id.rv_prodPrice);
            parentLayout = itemView.findViewById(R.id.rv_containerLayout);
        }
    }



}
