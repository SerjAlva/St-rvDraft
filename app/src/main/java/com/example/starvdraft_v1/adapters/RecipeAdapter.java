package com.example.starvdraft_v1.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.starvdraft_v1.R;
import com.example.starvdraft_v1.activities.WatchRecipeActivity;
import com.example.starvdraft_v1.holders.RecipeViewHolder;
import com.example.starvdraft_v1.models.Recipe;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeViewHolder> {

    private Context context;
    private ArrayList<Recipe> items;

    public RecipeAdapter(ArrayList<Recipe> items) {
        this.items = items;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemview = LayoutInflater.from(context).inflate(R.layout.card_recipe,parent,false);
        return new RecipeViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        holder.updateUI(items.get(position));

        holder.iv_RecipeCardImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String extraData = holder.tv_RecipeCardName.getText().toString();
                Intent intent = new Intent(context, WatchRecipeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("RECIPE", extraData);
                Log.i("Extra", extraData);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
