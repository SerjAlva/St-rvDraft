package com.example.starvdraft_v1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.starvdraft_v1.R;
import com.example.starvdraft_v1.holders.PrepStepViewHolder;
import com.example.starvdraft_v1.models.PrepStep;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class PrepStepAdapter extends RecyclerView.Adapter<PrepStepViewHolder> {

    private Context context;
    private ArrayList<PrepStep> items;

    public PrepStepAdapter(ArrayList<PrepStep> items) {
        this.items = items;
    }

    @Override
    public PrepStepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemview = LayoutInflater.from(context).inflate(R.layout.card_prepstep,parent,false);
        return new PrepStepViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(PrepStepViewHolder holder, int position) {
        holder.updateUI(items.get(position));

        holder.iv_DeleteStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                notifyItemRangeChanged(holder.getAdapterPosition(), items.size());
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
