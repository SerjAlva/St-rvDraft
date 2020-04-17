package com.example.starvdraft_v1.network.requests;

import com.example.starvdraft_v1.models.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetRecipe {

    @GET("consultaReceta.php")
    Call<Recipe> getRecipe(@Query("Receta") String Receta);

}
