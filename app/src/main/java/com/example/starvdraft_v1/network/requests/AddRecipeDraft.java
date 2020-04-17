package com.example.starvdraft_v1.network.requests;

import com.example.starvdraft_v1.models.Recipe;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AddRecipeDraft {

    @POST("agregarBorradorReceta.php")
    Call<Recipe> saveRecipeDraft(@Query("Receta") String Receta,
                                 @Query("Porcion") String Porcion,
                                 @Query("TiempoP") String TiempoP,
                                 @Query("TiempoC") String TiempoC,
                                 @Query("Preparacion") String Preparacion,
                                 @Query("IngredientesD") String IngredientesD,
                                 @Query("Autor") String Autor,
                                 @Query("Calificacion") String Calificacion,
                                 @Query("Capturas") String Capturas,
                                 @Query("Categoria") String Categoria);

}
