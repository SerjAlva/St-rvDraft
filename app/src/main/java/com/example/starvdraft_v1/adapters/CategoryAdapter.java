package com.example.starvdraft_v1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.starvdraft_v1.R;
import com.example.starvdraft_v1.holders.CategoryViewHolder;
import com.example.starvdraft_v1.models.FoodCategory;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder> {

    private Context context;
    private ArrayList<FoodCategory> items;

    public CategoryAdapter(ArrayList<FoodCategory> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemview = LayoutInflater.from(context).inflate(R.layout.card_category, parent, false);
        return new CategoryViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryViewHolder holder, final int position) {

        holder.updateUI(items.get(position), context);
        holder.setRecyclerviewVisibility(items.get(position).isDeployed());
        holder.ivDesplegar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FoodCategory categoriaAlimento = items.get(holder.getAdapterPosition());
                categoriaAlimento.setDeployment(!categoriaAlimento.isDeployed());
                notifyItemChanged(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
