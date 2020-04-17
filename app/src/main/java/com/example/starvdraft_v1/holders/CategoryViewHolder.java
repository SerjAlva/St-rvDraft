package com.example.starvdraft_v1.holders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.starvdraft_v1.R;
import com.example.starvdraft_v1.adapters.ItemListAdapter;
import com.example.starvdraft_v1.models.FoodCategory;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryViewHolder extends RecyclerView.ViewHolder {

    TextView tvTitulo, tvProgreso;
    RecyclerView rvElementosCategoria;
    public ImageView ivDesplegar;
    RelativeLayout rlContenedorRelativo;


    public CategoryViewHolder(View itemView){
        super(itemView);
        tvTitulo = (TextView) itemView.findViewById(R.id.tvNombreCategoria);
        rvElementosCategoria = (RecyclerView) itemView.findViewById(R.id.rvLista);
        ivDesplegar = (ImageView) itemView.findViewById(R.id.ivDesplegar);
        rlContenedorRelativo = (RelativeLayout) itemView.findViewById(R.id.rlContenedorRelativo);
        tvProgreso = (TextView) itemView.findViewById(R.id.tvProgreso);
    }

    public void setRecyclerviewVisibility(boolean nuevoEstado){
        if(nuevoEstado) {
            ivDesplegar.setImageResource(R.drawable.ic_arrow_up_black_24dp);
            rvElementosCategoria.setVisibility(View.VISIBLE);
            tvProgreso.setVisibility(View.GONE);
        }
        else {
            ivDesplegar.setImageResource(R.drawable.ic_arrow_down);
            rvElementosCategoria.setVisibility(View.GONE);
            tvProgreso.setVisibility(View.VISIBLE);
        }
    }



    public void updateUI(FoodCategory foodCategory, Context context){
        tvTitulo.setText(foodCategory.getTitulo());
        tvTitulo.setTextColor(context.getResources().getColor(R.color.blanco));
        tvProgreso.setTextColor(context.getResources().getColor(R.color.blanco));
        rlContenedorRelativo.setBackgroundColor(context.getResources().getColor(foodCategory.color));
        rvElementosCategoria.setHasFixedSize(true);
        rvElementosCategoria.setLayoutManager(new LinearLayoutManager(context));
        rvElementosCategoria.setAdapter(new ItemListAdapter(foodCategory.getElementos()));
        foodCategory.contarListos();
        tvProgreso.setText(foodCategory.getElementosListos() + "/" + foodCategory.getNumElementos());
    }

}
