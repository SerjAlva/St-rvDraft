package com.example.starvdraft_v1.activities;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.starvdraft_v1.R;
import com.example.starvdraft_v1.models.Recipe;
import com.example.starvdraft_v1.network.ApiUtils;
import com.example.starvdraft_v1.network.requests.GetDishList;

import java.util.ArrayList;
import java.util.List;

public class PassActivity extends AppCompatActivity {

    Button btn_Search, btn_Consult, btn_Create, btn_Home;
    ArrayList<Recipe> dishList;
    GetDishList getDishList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass);

        btn_Search = (Button) findViewById(R.id.btn_Search);
        btn_Create = (Button) findViewById(R.id.btn_Create);
        btn_Home = (Button) findViewById(R.id.btn_Home);
        getDishList = ApiUtils.getDishList();
        dishList = new ArrayList<>();

        btn_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDishList("Hotcakes::Pepinos marineros");
            }
        });

        btn_Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PassActivity.this, CreateRecipeActivity.class);
                startActivity(intent);
            }
        });

        btn_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PassActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

    }

    public void getDishList(String recetas) {
        getDishList.getDishList(recetas).enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {

                if(response.isSuccessful()) {
                    Log.i("RestServiceDishList", "SUCCESS" + response.body().toString());
                    Log.i("RestServiceDishList", call.request().toString());

                    List<Recipe> recipes = response.body();

                    for (Recipe encodedRecipe : recipes) {
                        Recipe recipe = new Recipe();
                        Log.i("RestServiceDishList", encodedRecipe.getIngredientesD());
                        recipe.setReceta(encodedRecipe.getReceta());
                        recipe.setPorcion(encodedRecipe.getPorcion());
                        recipe.setNombre(encodedRecipe.getNombre() + " " + encodedRecipe.getApellido());
                        recipe.setCalificacion(encodedRecipe.getCalificacion());
                        dishList.add(recipe);

                    }
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.i("RestServiceSuggestions", String.valueOf(t));
            }
        });
    }
}
