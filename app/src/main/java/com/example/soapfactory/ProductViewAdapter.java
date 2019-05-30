package com.example.soapfactory;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

//Later change this to a fragment
public class ProductViewAdapter extends RecyclerView.Adapter<ProductViewAdapter.ProductViewHolder>{

    Context mContext;
    //List<Product> mName;
    //List<Product> mDescription;
    //List<Product> mData;
    //List<Product> mDataFiltered;
    private ArrayList<String> mName;
    private ArrayList<String> mDescription;

    public ProductViewAdapter(Context mContext, ArrayList<String> mName, ArrayList<String> mDescription) {
        this.mContext = mContext;
        this.mName = mName;
        this.mDescription = mDescription;
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        public TextView prodName, prodDescription;
        public ProductViewHolder(View itemView) {
            super(itemView);
            prodName = itemView.findViewById(R.id.rv_prodName);
            prodDescription = itemView.findViewById(R.id.rv_prodDescription);
        }
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ProductViewAdapter.ProductViewHolder onCreateViewHolder(ViewGroup parent,
                                                                   int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_product, parent, false);
        ProductViewHolder prodHolder = new ProductViewHolder(view);
        return prodHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewAdapter.ProductViewHolder productViewHolder, int position) {
        //productViewHolder.prodName.setText(mDataFiltered.get(position).getName());
        //productViewHolder.prodDescription.setText(mDataFiltered.get(position).getDescription());
        productViewHolder.prodName.setText(mName.get(position));
        productViewHolder.prodDescription.setText(mDescription.get(position));


    }

    //Defines the size of the recycleview list
    @Override
    public int getItemCount() {
        return mName.size();
    }

    //This is being implemented because the recycleView is returning blank
    //Probably has to do with List<Product> instead of ArrayList<String>
    /*@Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                String Key = constraint.toString();
                if (Key.isEmpty()) {

                    mDataFiltered = mData ;

                }
                else {
                    List<Product> lstFiltered = new ArrayList<>();
                    for (Product row : mData) {

                        if (row.getName().toLowerCase().contains(Key.toLowerCase())){
                            lstFiltered.add(row);
                        }

                    }

                    mDataFiltered = lstFiltered;

                }


                FilterResults filterResults = new FilterResults();
                filterResults.values= mDataFiltered;
                return filterResults;

            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {


                mDataFiltered = (List<Product>) results.values;
                notifyDataSetChanged();

            }
        };
    }*/


}
