package com.example.starvdraft_v1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.starvdraft_v1.R;
import com.example.starvdraft_v1.holders.ItemListViewHolder;
import com.example.starvdraft_v1.models.MarketListItem;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class    ItemListAdapter extends RecyclerView.Adapter<ItemListViewHolder> {

    private Context context;
    private ArrayList<MarketListItem> items;

    public ItemListAdapter(ArrayList<MarketListItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ItemListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemview = LayoutInflater.from(context).inflate(R.layout.card_market_element, parent, false);

        return new ItemListViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemListViewHolder holder, final int position) {
        holder.updateUI(items.get(position));
        holder.chBxEstado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    items.get(position).setStatus(true);
                    holder.itemReady();
                }else{
                    items.get(position).setStatus(false);
                    holder.itemUnready();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
