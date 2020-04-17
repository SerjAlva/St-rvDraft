package com.example.starvdraft_v1.preferences;

import android.content.Context;
import android.content.SharedPreferences;

/* 1 En esta clase guardamos los datos de usuario de manera que quede registrado en la memoria de la app*/

public class AppPreferences {

    private SharedPreferences appData;
    private SharedPreferences.Editor appEditor;

    public AppPreferences(Context context){
        appData= context.getSharedPreferences("dataApp", Context.MODE_PRIVATE);
    }

    public void setLoginStatus(boolean status){
        appEditor= appData.edit();
        appEditor.putBoolean("loginStatus", status);
        appEditor.apply();
    }

    public boolean isLogued(){
        return appData.getBoolean("loginStatus", false);
    }

    public void setUserName(String value){
        appEditor= appData.edit();
        appEditor.putString("userName", value);
        appEditor.apply();
    }

    public String getUserName(){
        return appData.getString("userName", "");
    }

    public void setUserLastName(String value){
        appEditor= appData.edit();
        appEditor.putString("UserLastName", value);
        appEditor.apply();
    }

    public String getUserLastName(){
        return appData.getString("UserLastName", "");
    }

    public void setUserEmail(String value){
        appEditor= appData.edit();
        appEditor.putString("userEmail", value);
        appEditor.apply();
    }

    public String getUserEmail(){
        return appData.getString("userEmail", "");
    }

    public void setUserPassword(String value){
        appEditor= appData.edit();
        appEditor.putString("userPassword", value);
        appEditor.apply();
    }

    public String getUserPassword(){
        return appData.getString("userPassword", "");
    }

    public void setUserGender(String value){
        appEditor= appData.edit();
        appEditor.putString("userGender", value);
        appEditor.apply();
    }

    public String getUserGender(){
        return appData.getString("userGender", "");
    }

    public void setUserAge(String value){
        appEditor= appData.edit();
        appEditor.putString("userAge", value);
        appEditor.apply();
    }

    public String getUserAge(){
        return appData.getString("userAge", "");
    }

    public void setUserAvatar(String value){
        appEditor= appData.edit();
        appEditor.putString("userAvatar", value);
        appEditor.apply();
    }

    public String getUserAvatar(){
        return appData.getString("userAvatar", "");
    }



    public String getUserDishList(){
        return appData.getString("userDishList", "");
    }

    public void setUserDishList(String value){
        appEditor= appData.edit();
        appEditor.putString("userDishList", value);
        appEditor.apply();
    }

}


