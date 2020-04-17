package com.example.starvdraft_v1.network.requests;

import com.example.starvdraft_v1.models.User;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RegisterUser {

    @POST("registroUsuario.php")
    Call<User> saveUser(@Query("Nombre") String Nombre,
                        @Query("Apellido") String Apellido,
                        @Query("Edad") Integer Edad,
                        @Query("Genero") String Genero,
                        @Query("Correo") String Correo,
                        @Query("Psswd") String Psswd,
                        @Query("Avatar") String Avatar);
}
