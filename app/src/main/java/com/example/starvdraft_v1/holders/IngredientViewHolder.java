package com.example.starvdraft_v1.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.starvdraft_v1.R;
import com.example.starvdraft_v1.models.Ingredient;

import androidx.recyclerview.widget.RecyclerView;

public class IngredientViewHolder extends RecyclerView.ViewHolder {

    TextView tv_Ingredient;
    public ImageView iv_DeleteIngredient;

    public IngredientViewHolder(View itemView) {
        super(itemView);

        tv_Ingredient = (TextView) itemView.findViewById(R.id.tv_Ingredient);
        iv_DeleteIngredient = (ImageView) itemView.findViewById(R.id.iv_DeleteIngredient);

    }

    public void updateUI(Ingredient ingredient) {

        String formattedIngredient = ingredient.getiQuantity() + " " + ingredient.getiUnits() + " de " + ingredient.getiName() + " " + ingredient.getiComment();
        tv_Ingredient.setText(formattedIngredient);

    }

}
