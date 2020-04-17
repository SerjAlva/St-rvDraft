package com.example.starvdraft_v1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.starvdraft_v1.R;
import com.example.starvdraft_v1.holders.IngredientViewHolder;
import com.example.starvdraft_v1.models.Ingredient;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientViewHolder> {

    private Context context;
    private ArrayList<Ingredient> items;

    public IngredientAdapter(ArrayList<Ingredient> items) {
        this.items = items;
    }

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemview = LayoutInflater.from(context).inflate(R.layout.card_ingredient,parent,false);
        return new IngredientViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder holder, int position) {
        holder.updateUI(items.get(position));

        holder.iv_DeleteIngredient.setOnClickListener(new View.OnClickListener() {
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
