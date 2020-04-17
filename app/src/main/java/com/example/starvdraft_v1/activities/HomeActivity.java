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
import android.widget.Button;
import android.widget.Toast;

import com.example.starvdraft_v1.R;
import com.example.starvdraft_v1.adapters.RecipeAdapter;
import com.example.starvdraft_v1.models.Recipe;
import com.example.starvdraft_v1.network.ApiUtils;
import com.example.starvdraft_v1.network.requests.GetRecipes;
import com.example.starvdraft_v1.preferences.AppPreferences;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    Button btn_Discover, btn_RecipeBook, btn_MyPlan;
    RecyclerView recyclerBreakfast, recyclerLunch, recyclerDinner, recyclerCollections;
    RecipeAdapter breakfastsAdapter, lunchesAdapter, dinnersAdapter, collectionsAdapter;
    public ArrayList<Recipe> breakfastsList, lunchesList, dinnersList, collectionsList;
    GetRecipes getRecipes;
    AppPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btn_Discover = (Button) findViewById(R.id.btn_Discover);
        btn_RecipeBook = (Button) findViewById(R.id.btn_RecipeBook);
        btn_MyPlan = (Button) findViewById(R.id.btn_MyPlan);
        recyclerBreakfast = (RecyclerView) findViewById(R.id.recyclerBreakfast);
        recyclerLunch = (RecyclerView) findViewById(R.id.recyclerLunch);
        recyclerDinner = (RecyclerView) findViewById(R.id.recyclerDinner);
        recyclerCollections = (RecyclerView) findViewById(R.id.recyclerCollections);

        prefs = new AppPreferences(HomeActivity.this);

        breakfastsList = new ArrayList<>();
        lunchesList = new ArrayList<>();
        dinnersList = new ArrayList<>();
        collectionsList = new ArrayList<>();
        getRecipes = ApiUtils.getRecipes();

        recyclerBreakfast.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerBreakfast.setLayoutManager(linearLayoutManager);

        recyclerLunch.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerLunch.setLayoutManager(linearLayoutManager2);

        recyclerDinner.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(this);
        linearLayoutManager3.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerDinner.setLayoutManager(linearLayoutManager3);

        recyclerCollections.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager4 = new LinearLayoutManager(this);
        linearLayoutManager4.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerCollections.setLayoutManager(linearLayoutManager4);

        getRecipeList();

        btn_Discover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,DishListActivity.class);
                startActivity(intent);
            }
        });

        btn_RecipeBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefs.setUserDishList("");
            }
        });


    }

    public void getRecipeList() {
        getRecipes.getRecipeList().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {

                if(response.isSuccessful()) {
                    Log.i("RestService", "SUCCESS" + response.body().toString());

                    List<Recipe> recipes = response.body();

                    for (Recipe encodedRecipe : recipes) {
                        Recipe recipe = new Recipe();
                        recipe.setReceta(encodedRecipe.getReceta());
                        recipe.setPorcion(encodedRecipe.getPorcion());
                        recipe.setNombre(encodedRecipe.getNombre() + " " + encodedRecipe.getApellido());
                        recipe.setCalificacion(encodedRecipe.getCalificacion());

                        switch (encodedRecipe.getCategoria()){

                            case ("Desayuno"):
                                breakfastsList.add(recipe);
                                break;
                            case "Comida":
                                lunchesList.add(recipe);
                                break;
                            case "Cena":
                                dinnersList.add(recipe);
                                break;
                            default:
                                collectionsList.add(recipe);
                                break;

                        }
                    }

                    showAllBreakfasts();
                    showAllLunches();
                    showAllDinners();
                    showAllCollections();

                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.i("RestService", String.valueOf(t));
            }
        });
    }

    private void showAllBreakfasts(){
        breakfastsAdapter = new RecipeAdapter(breakfastsList);
        recyclerBreakfast.setAdapter(breakfastsAdapter);
    }

    private void showAllLunches(){
        lunchesAdapter = new RecipeAdapter(lunchesList);
        recyclerLunch.setAdapter(lunchesAdapter);
    }

    private void showAllDinners(){
        dinnersAdapter = new RecipeAdapter(dinnersList);
        recyclerDinner.setAdapter(dinnersAdapter);
    }

    private void showAllCollections(){
        collectionsAdapter = new RecipeAdapter(collectionsList);
        recyclerCollections.setAdapter(collectionsAdapter);
    }


}
