package com.example.starvdraft_v1.extras;

import android.widget.EditText;

import java.util.ArrayList;

public class TextControl {

    public static boolean fieldsNotEmpty(EditText[] fields, String fieldErrorMessage){
        for (EditText field:fields){
            String value= field.getText().toString();
            if (value.equals("")){
                field.setError(fieldErrorMessage);
                return false;
            }
        }
        return true;
    }

    public static boolean passwordsMatch(String password, String passwordConfirm){
        return password.equals(passwordConfirm);
    }

    public static String[] getTextFromFields(EditText[] fields){
        String[] values= new String[fields.length];
        for (int i=0;i<values.length;i++){
            values[i]= fields[i].getText().toString();
        }
        return values;
    }

    public static String formatRecipeSteps(String preparation){
        String[] prepSteps = preparation.split("::");
        String formattedPrepSteps="";
        Integer count=1;
        for (String prepStep : prepSteps) {
            formattedPrepSteps = formattedPrepSteps + count + ".- " + prepStep + "\n\n";
            count++;
        }
        return formattedPrepSteps;
    }

    public static String formatRecipeIngredients(String ingredients){
        String[] cleanIngredients = ingredients.split("::");
        String formattedIngredients="";
        Integer count=1;
        for (String cleanIngredient : cleanIngredients) {
            String[] ingredientPart = cleanIngredient.split(":");
            formattedIngredients = formattedIngredients + count + ".- " + ingredientPart[1] + " " + ingredientPart[2] + " de " + ingredientPart[0] + " " + ingredientPart[3] + "\n\n";
            count++;
        }
        return formattedIngredients;
    }

}
