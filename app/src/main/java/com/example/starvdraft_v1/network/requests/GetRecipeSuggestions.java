package com.example.starvdraft_v1.network.requests;

import com.example.starvdraft_v1.models.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetRecipeSuggestions {

    @GET("consultaSugerenciasRecetas.php")
    Call<List<Recipe>> getRecipeSuggestionList();

}
