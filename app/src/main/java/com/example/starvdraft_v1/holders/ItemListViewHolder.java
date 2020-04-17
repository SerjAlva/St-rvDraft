package com.example.starvdraft_v1.holders;

import android.graphics.Paint;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.starvdraft_v1.R;

import androidx.recyclerview.widget.RecyclerView;

public class ItemListViewHolder extends RecyclerView.ViewHolder {

    public TextView tvContenido;
    public CheckBox chBxEstado;

    public ItemListViewHolder(View itemView){
        super(itemView);
        tvContenido = (TextView) itemView.findViewById(R.id.tvElement);
        chBxEstado = (CheckBox) itemView.findViewById(R.id.chBxListo);
    }

    public void itemReady(){
        tvContenido.setPaintFlags(tvContenido.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
    }

    public void itemUnready(){
        tvContenido.setPaintFlags(0);
    }

}
