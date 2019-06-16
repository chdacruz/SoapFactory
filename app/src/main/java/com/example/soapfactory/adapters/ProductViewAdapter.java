package com.example.soapfactory.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.soapfactory.R;
import com.example.soapfactory.models.Product;

import java.util.ArrayList;

//Later change this to a fragment
//public class ProductViewAdapter extends RecyclerView.Adapter<ProductViewAdapter.ViewHolder> implements Filterable {
public class ProductViewAdapter extends RecyclerView.Adapter<ProductViewAdapter.ViewHolder> {

    Context mContext;
    private ArrayList<Product> products;
    private ArrayList<Product> productsFiltered;

    public ProductViewAdapter(Context context, ArrayList<Product> products) {
        this.mContext = context;
        this.products = products;
        this.productsFiltered = products;
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

        //Card animation
        holder.parentLayout.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.animation_card_fade));

        holder.prodName.setText(products.get(position).getName());
        holder.prodDescription.setText(products.get(position).getDescription());
        holder.prodType.setText(products.get(position).getType());
        holder.prodPrice.setText(products.get(position).getPrice());

        holder.parentLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //Toast.makeText(mContext, mNames.get(position), Toast.LENGTH_SHORT).show();
                Toast.makeText(mContext, (CharSequence) productsFiltered.get(position), Toast.LENGTH_SHORT).show();
            }

        });
    }

    //Defines the size of the recycleview list
    @Override
    public int getItemCount() {
        //return mNames.size();
        return productsFiltered.size();
    }

    /*
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String Key = constraint.toString();
                if (Key.isEmpty()) { productsFiltered = products; }
                else {
                    ArrayList<Product> lstFiltered = new ArrayList<>();
                    for (Product row : products) {

                        if (row.getName().toLowerCase().contains(Key.toLowerCase())){
                            lstFiltered.add(row);
                        }
                    }
                    productsFiltered = lstFiltered;

                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = productsFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                productsFiltered = (ArrayList<Product>) results.values;
                notifyDataSetChanged();

            }
        };
    }*/

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView prodName, prodDescription, prodType, prodPrice;
        //RelativeLayout parentLayout;
        RelativeLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            prodName = itemView.findViewById(R.id.rv_prodName);
            prodDescription = itemView.findViewById(R.id.rv_prodDescription);
            prodType = itemView.findViewById(R.id.rv_prodType);
            prodPrice = itemView.findViewById(R.id.rv_prodPrice);
            parentLayout = itemView.findViewById(R.id.rv_productContainerLayout);

        }
    }



}
