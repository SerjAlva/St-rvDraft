package com.example.starvdraft_v1.network;

import com.example.starvdraft_v1.network.requests.AddRecipe;
import com.example.starvdraft_v1.network.requests.AddRecipeDraft;
import com.example.starvdraft_v1.network.requests.GetDishList;
import com.example.starvdraft_v1.network.requests.GetRecipe;
import com.example.starvdraft_v1.network.requests.GetRecipeSuggestions;
import com.example.starvdraft_v1.network.requests.GetRecipes;
import com.example.starvdraft_v1.network.requests.GetUser;
import com.example.starvdraft_v1.network.requests.RegisterUser;

public class ApiUtils {

    public static final String STARV_BASE_URL = "http://192.168.0.1/ServidorDL/Starv_Services/";

    public static GetUser getUser() {

        return RetrofitClient.getClient(STARV_BASE_URL).create(GetUser.class);
    }

    public static RegisterUser registerUser() {

        return RetrofitClient.getClient(STARV_BASE_URL).create(RegisterUser.class);
    }

    public static AddRecipe addRecipe() {

        return RetrofitClient.getClient(STARV_BASE_URL).create(AddRecipe.class);
    }

    public static AddRecipeDraft addRecipeDraft() {

        return RetrofitClient.getClient(STARV_BASE_URL).create(AddRecipeDraft.class);
    }

    public static GetRecipes getRecipes() {

        return RetrofitClient.getClient(STARV_BASE_URL).create(GetRecipes.class);
    }

    public static GetRecipe getRecipe() {

        return RetrofitClient.getClient(STARV_BASE_URL).create(GetRecipe.class);
    }

    public static GetRecipeSuggestions getRecipeSuggestions() {

        return RetrofitClient.getClient(STARV_BASE_URL).create(GetRecipeSuggestions.class);
    }

    public static GetDishList getDishList() {

        return RetrofitClient.getClient(STARV_BASE_URL).create(GetDishList.class);
    }

}