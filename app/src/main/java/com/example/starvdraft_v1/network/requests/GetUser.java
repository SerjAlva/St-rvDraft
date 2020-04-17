package com.example.starvdraft_v1.network.requests;

import com.example.starvdraft_v1.models.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetUser {

    @GET("consultaUsuario.php")
    Call<User> getUser(@Query("Correo") String Correo,
                       @Query("Psswd") String Psswd);

}
