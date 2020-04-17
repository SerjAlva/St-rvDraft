package com.example.starvdraft_v1.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.starvdraft_v1.R;
import com.example.starvdraft_v1.adapters.RecipeAdapter;
import com.example.starvdraft_v1.extras.TextControl;
import com.example.starvdraft_v1.models.Recipe;
import com.example.starvdraft_v1.network.ApiUtils;
import com.example.starvdraft_v1.network.requests.GetRecipe;
import com.example.starvdraft_v1.network.requests.GetRecipeSuggestions;
import com.example.starvdraft_v1.network.requests.GetRecipes;
import com.example.starvdraft_v1.preferences.AppPreferences;

import java.util.ArrayList;
import java.util.List;


public class WatchRecipeActivity extends AppCompatActivity {

    TextView tv_RecipeName, tv_IngredientList, tv_PrepStepList, tv_NutriInfoList, tv_Portions, tv_RecipeControl;
    ImageView iv_showIngredients, iv_showPrepSteps, iv_showNutriInfo,iv_RecipeImage, iv_plusPortion, iv_lessPortion, iv_CreateList;
    RecyclerView recyclerSuggestions;
    RecipeAdapter suggestionsAdapter;
    ArrayList<Recipe> collectionsList;
    GetRecipe getRecipe;
    GetRecipeSuggestions getRecipeSuggestions;
    AppPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_recipe);

        tv_RecipeName = (TextView) findViewById(R.id.tv_RecipeName);
        tv_IngredientList = (TextView) findViewById(R.id.tv_IngredientList);
        tv_PrepStepList = (TextView) findViewById(R.id.tv_PrepStepList);
        tv_NutriInfoList = (TextView) findViewById(R.id.tv_NutriInfoList);
        tv_Portions = (TextView) findViewById(R.id.tv_Portions);
        tv_RecipeControl = (TextView) findViewById(R.id.tv_RecipeControl);
        iv_showIngredients = (ImageView) findViewById(R.id.iv_showIngredients);
        iv_showPrepSteps = (ImageView) findViewById(R.id.iv_showPrepSteps);
        iv_showNutriInfo = (ImageView) findViewById(R.id.iv_showNutriInfo);
        iv_RecipeImage = (ImageView) findViewById(R.id.iv_RecipeImage);
        iv_plusPortion = (ImageView) findViewById(R.id.iv_plusPortion);
        iv_lessPortion = (ImageView) findViewById(R.id.iv_lessPortion);
        iv_CreateList = (ImageView) findViewById(R.id.iv_CreateList);
        recyclerSuggestions = (RecyclerView) findViewById(R.id.recyclerSuggestions);

        prefs = new AppPreferences(WatchRecipeActivity.this);
        collectionsList = new ArrayList<>();

        recyclerSuggestions.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerSuggestions.setLayoutManager(linearLayoutManager);

        getRecipe = ApiUtils.getRecipe();
        getRecipeSuggestions = ApiUtils.getRecipeSuggestions();

        String recipeName = getIntent().getStringExtra("RECIPE");

        getRecipe(recipeName);
        getRecipeSuggestionsList();

        iv_CreateList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String AddedRecipes = prefs.getUserDishList() + recipeName + "::";
                prefs.setUserDishList(AddedRecipes);
                Log.i("Pruebas",prefs.getUserDishList());
            }
        });

        iv_lessPortion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int portion = Integer.parseInt(tv_Portions.getText().toString());
                if (portion <= 2){
                    Toast.makeText(WatchRecipeActivity.this,"Imposible disminuir porciones",Toast.LENGTH_SHORT).show();
                } else {
                    int newPortion = (portion / 2);
                    String result = String.valueOf(newPortion);
                    tv_Portions.setText(result);
                }
            }
        });

        iv_plusPortion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int portion = Integer.parseInt(tv_Portions.getText().toString());
                int newPortion = (portion * 2);
                String result = String.valueOf(newPortion);
                tv_Portions.setText(result);
            }
        });

        iv_showIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tv_IngredientList.getVisibility() == View.GONE){
                    iv_showIngredients.setBackgroundResource(R.drawable.hide_icon);
                    tv_IngredientList.setVisibility(View.VISIBLE);
                } else {
                    iv_showIngredients.setBackgroundResource(R.drawable.show_icon);
                    tv_IngredientList.setVisibility(View.GONE);
                }
            }
        });

        iv_showPrepSteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv_PrepStepList.getVisibility() == View.GONE){
                    iv_showPrepSteps.setBackgroundResource(R.drawable.hide_icon);
                    tv_PrepStepList.setVisibility(View.VISIBLE);
                } else {
                    iv_showPrepSteps.setBackgroundResource(R.drawable.show_icon);
                    tv_PrepStepList.setVisibility(View.GONE);
                }
            }
        });

        iv_showNutriInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv_NutriInfoList.getVisibility() == View.GONE){
                    iv_showNutriInfo.setBackgroundResource(R.drawable.hide_icon);
                    tv_NutriInfoList.setVisibility(View.VISIBLE);
                } else {
                    iv_showNutriInfo.setBackgroundResource(R.drawable.show_icon);
                    tv_NutriInfoList.setVisibility(View.GONE);
                }
            }
        });



    }


    public void getRecipe(String receta) {
        getRecipe.getRecipe(receta).enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {

                if(response.isSuccessful()) {

                    //Log.i("RestService", "SUCCESS" + response.body().toString());

                    Recipe recipe = response.body();
                    String preparation, ingredients,formattedPrepSteps, formattedIngredients, portion;
                    preparation = recipe.getPreparacion();
                    ingredients = recipe.getIngredientesD();
                    portion = recipe.getPorcion();

                    formattedPrepSteps = TextControl.formatRecipeSteps(preparation);
                    formattedIngredients = TextControl.formatRecipeIngredients(ingredients);

                    tv_RecipeName.setText(recipe.getReceta());
                    tv_IngredientList.setText(formattedIngredients);
                    tv_PrepStepList.setText(formattedPrepSteps);

                    switch (recipe.getReceta()) {
                        case "Atún a la vizcaina":
                            iv_RecipeImage.setBackgroundResource(R.drawable.atun);
                            break;
                        case "Lechón horneado":
                            iv_RecipeImage.setBackgroundResource(R.drawable.lechon);
                            break;
                        case "Chilaquiles verdes":
                            iv_RecipeImage.setBackgroundResource(R.drawable.chilaquiles);
                            break;
                        case "Hotcakes":
                            iv_RecipeImage.setBackgroundResource(R.drawable.hotcakes);
                            break;
                        case "Huevos motuleños":
                            iv_RecipeImage.setBackgroundResource(R.drawable.huevos);
                            break;
                        case "Fajitas de pollo":
                            iv_RecipeImage.setBackgroundResource(R.drawable.fajitas);
                            break;
                        case "Filete Mignon":
                            iv_RecipeImage.setBackgroundResource(R.drawable.filete);
                            break;
                        case "Club Sandwich":
                            iv_RecipeImage.setBackgroundResource(R.drawable.club_sandwich);
                            break;
                        case "Crocante de maíz":
                            iv_RecipeImage.setBackgroundResource(R.drawable.crocante);
                            break;
                        case "Bowl de avena":
                            iv_RecipeImage.setBackgroundResource(R.drawable.bowl);
                            break;
                        case "Pepinos marineros":
                            iv_RecipeImage.setBackgroundResource(R.drawable.pepinos);
                            break;
                        case "Tostadas verdes":
                            iv_RecipeImage.setBackgroundResource(R.drawable.tostadas_verdes);
                            break;
                        case "Sopa de arroz":
                            iv_RecipeImage.setBackgroundResource(R.drawable.sopa);
                            break;
                        case "Ensalada de atún":
                            iv_RecipeImage.setBackgroundResource(R.drawable.ensalada_atun);
                            break;
                    }

                }
            }

            @Override
            public void onFailure(Call<Recipe> call, Throwable t) {
                Log.i("RestServiceFailure", String.valueOf(t));
                Log.i("RestService", call.request().toString());
            }
        });
    }

    public void getRecipeSuggestionsList() {
        getRecipeSuggestions.getRecipeSuggestionList().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {

                if(response.isSuccessful()) {
                    Log.i("RestServiceSuggestions", "SUCCESS" + response.body().toString());

                    List<Recipe> recipes = response.body();

                    for (Recipe encodedRecipe : recipes) {
                        Recipe recipe = new Recipe();
                        recipe.setReceta(encodedRecipe.getReceta());
                        recipe.setPorcion(encodedRecipe.getPorcion());
                        recipe.setNombre(encodedRecipe.getNombre() + " " + encodedRecipe.getApellido());
                        recipe.setCalificacion(encodedRecipe.getCalificacion());

                        collectionsList.add(recipe);
                    }

                    showAllSuggestions();

                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.i("RestServiceSuggestions", String.valueOf(t));
            }
        });
    }

    private void showAllSuggestions(){
        suggestionsAdapter = new RecipeAdapter(collectionsList);
        recyclerSuggestions.setAdapter(suggestionsAdapter);
    }


}
