package com.example.starvdraft_v1.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.starvdraft_v1.R;
import com.example.starvdraft_v1.models.Recipe;

import androidx.recyclerview.widget.RecyclerView;

public class RecipeViewHolder extends RecyclerView.ViewHolder {

    public ImageView iv_RecipeCardImg, iv_CardRecipeUserImage, iv_CardRecipeGrade;
    public TextView tv_RecipeCardName, tv_CardRecipeUserName;

    public RecipeViewHolder(View itemView) {
        super(itemView);

        iv_RecipeCardImg = (ImageView) itemView.findViewById(R.id.iv_RecipeCardImg);
        iv_CardRecipeUserImage = (ImageView) itemView.findViewById(R.id.iv_CardRecipeUserImage);
        iv_CardRecipeGrade = (ImageView) itemView.findViewById(R.id.iv_CardRecipeGrade);
        tv_RecipeCardName = (TextView) itemView.findViewById(R.id.tv_RecipeCardName);
        tv_CardRecipeUserName = (TextView) itemView.findViewById(R.id.tv_CardRecipeUserName);

    }

    public void updateUI(Recipe recipe) {

        switch (recipe.getReceta()) {
            case "Atún a la vizcaina":
                iv_RecipeCardImg.setBackgroundResource(R.drawable.atun);
                break;
            case "Lechón horneado":
                iv_RecipeCardImg.setBackgroundResource(R.drawable.lechon);
                break;
            case "Chilaquiles verdes":
                iv_RecipeCardImg.setBackgroundResource(R.drawable.chilaquiles);
                break;
            case "Hotcakes":
                iv_RecipeCardImg.setBackgroundResource(R.drawable.hotcakes);
                break;
            case "Huevos motuleños":
                iv_RecipeCardImg.setBackgroundResource(R.drawable.huevos);
                break;
            case "Fajitas de pollo":
                iv_RecipeCardImg.setBackgroundResource(R.drawable.fajitas);
                break;
            case "Filete Mignon":
                iv_RecipeCardImg.setBackgroundResource(R.drawable.filete);
                break;
            case "Club Sandwich":
                iv_RecipeCardImg.setBackgroundResource(R.drawable.club_sandwich);
                break;
            case "Crocante de maíz":
                iv_RecipeCardImg.setBackgroundResource(R.drawable.crocante);
                break;
            case "Bowl de avena":
                iv_RecipeCardImg.setBackgroundResource(R.drawable.bowl);
                break;
            case "Pepinos marineros":
                iv_RecipeCardImg.setBackgroundResource(R.drawable.pepinos);
                break;
            case "Tostadas verdes":
                iv_RecipeCardImg.setBackgroundResource(R.drawable.tostadas_verdes);
                break;
            case "Sopa de arroz":
                iv_RecipeCardImg.setBackgroundResource(R.drawable.sopa);
                break;
            case "Ensalada de atún":
                iv_RecipeCardImg.setBackgroundResource(R.drawable.ensalada_atun);
                break;
        }

        switch (recipe.getNombre()) {
            case "Fernando Romo":
                iv_CardRecipeUserImage.setImageResource(R.drawable.fernando_avatar);
                break;
            case "Batman Wayne":
                iv_CardRecipeUserImage.setImageResource(R.drawable.batman_avatar);
                break;
        }

        switch (recipe.getCalificacion()) {
            case "1":
                iv_CardRecipeGrade.setImageResource(R.drawable.onestar_icon);
                break;
            case "2":
                iv_CardRecipeGrade.setImageResource(R.drawable.twostar_icon);
                break;
            case "3":
                iv_CardRecipeGrade.setImageResource(R.drawable.threestar_icon);
                break;
            case "4":
                iv_CardRecipeGrade.setImageResource(R.drawable.fourstar_icon);
                break;
            case "5":
                iv_CardRecipeGrade.setImageResource(R.drawable.fivestar_icon);
                break;
        }

        tv_RecipeCardName.setText(recipe.getReceta());
        tv_CardRecipeUserName.setText(recipe.getNombre());


    }

}
