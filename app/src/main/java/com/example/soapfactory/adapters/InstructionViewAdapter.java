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
import com.example.soapfactory.models.Instruction;

import java.util.ArrayList;

public class InstructionViewAdapter extends RecyclerView.Adapter<InstructionViewAdapter.ViewHolder> {

    Context mContext;
    private ArrayList<Instruction> instructions;
    private ArrayList<Instruction> instructionsFiltered;

    public InstructionViewAdapter(Context context, ArrayList<Instruction> instructions) {
        this.mContext = context;
        this.instructions = instructions;
        this.instructionsFiltered = instructions;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public InstructionViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_instruction, parent, false);
        InstructionViewAdapter.ViewHolder holder = new InstructionViewAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionViewAdapter.ViewHolder holder, final int position) {

        //Card animation
        holder.parentLayout.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.animation_card_fade));

        holder.instructionTitle.setText(instructions.get(position).getTitle());
        holder.instructionContent.setText(instructions.get(position).getContent());

        holder.parentLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //Toast.makeText(mContext, mNames.get(position), Toast.LENGTH_SHORT).show();
                Toast.makeText(mContext, (CharSequence) instructionsFiltered.get(position), Toast.LENGTH_SHORT).show();
            }

        });
    }

    //Defines the size of the recycleview list
    @Override
    public int getItemCount() {
        return instructionsFiltered.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView instructionTitle, instructionContent;
        //RelativeLayout parentLayout;
        RelativeLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            instructionTitle = itemView.findViewById(R.id.rv_instructionTitle);
            instructionContent = itemView.findViewById(R.id.rv_instructionContent);
            parentLayout = itemView.findViewById(R.id.rv_productContainerLayout);

        }
    }
}
