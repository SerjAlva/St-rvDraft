package com.example.starvdraft_v1.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.starvdraft_v1.R;
import com.example.starvdraft_v1.models.DishItem;

import androidx.recyclerview.widget.RecyclerView;

public class DishItemViewHolder extends RecyclerView.ViewHolder {

    TextView tvCantidadPorciones, tvPrecio, tvTitulo;
    public ImageView ibRemove, ivMenos, ivMas, ivPeople, ivImagen, ivRecordatorio;

    public DishItemViewHolder(View itemView) {
        super(itemView);

        tvCantidadPorciones = (TextView) itemView.findViewById(R.id.tvCantidadPorciones);
        tvPrecio = (TextView) itemView.findViewById(R.id.tvPrecio);
        tvTitulo = (TextView) itemView.findViewById(R.id.tvTitulo);
        ibRemove = (ImageView) itemView.findViewById(R.id.ibRemoves);
        ivMenos = (ImageView) itemView.findViewById(R.id.ivMenos);
        ivMas = (ImageView) itemView.findViewById(R.id.ivMas);
        ivPeople = (ImageView) itemView.findViewById(R.id.ivPeople);
        ivImagen = (ImageView) itemView.findViewById(R.id.ivImagen);
        ivRecordatorio = (ImageView) itemView.findViewById(R.id.ivRecordatorio);
    }

    public void updateUI(DishItem dishItem) {

        tvTitulo.setText(dishItem.getTitulo());
        tvPrecio.setText(String.valueOf(dishItem.getPrecio()));
        tvCantidadPorciones.setText(dishItem.getPorciones());

        switch (dishItem.getTitulo()) {
            case "Atún a la vizcaina":
                ivImagen.setBackgroundResource(R.drawable.atun);
                break;
            case "Lechón horneado":
                ivImagen.setBackgroundResource(R.drawable.lechon);
                break;
            case "Chilaquiles verdes":
                ivImagen.setBackgroundResource(R.drawable.chilaquiles);
                break;
            case "Hotcakes":
                ivImagen.setBackgroundResource(R.drawable.hotcakes);
                break;
            case "Huevos motuleños":
                ivImagen.setBackgroundResource(R.drawable.huevos);
                break;
            case "Fajitas de pollo":
                ivImagen.setBackgroundResource(R.drawable.fajitas);
                break;
            case "Filete Mignon":
                ivImagen.setBackgroundResource(R.drawable.filete);
                break;
            case "Club Sandwich":
                ivImagen.setBackgroundResource(R.drawable.club_sandwich);
                break;
            case "Crocante de maíz":
                ivImagen.setBackgroundResource(R.drawable.crocante);
                break;
            case "Bowl de avena":
                ivImagen.setBackgroundResource(R.drawable.bowl);
                break;
            case "Pepinos marineros":
                ivImagen.setBackgroundResource(R.drawable.pepinos);
                break;
            case "Tostadas verdes":
                ivImagen.setBackgroundResource(R.drawable.tostadas_verdes);
                break;
            case "Sopa de arroz":
                ivImagen.setBackgroundResource(R.drawable.sopa);
                break;
            case "Ensalada de atún":
                ivImagen.setBackgroundResource(R.drawable.ensalada_atun);
                break;
        }

    }


}
