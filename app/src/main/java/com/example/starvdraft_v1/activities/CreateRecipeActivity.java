package com.example.starvdraft_v1.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.starvdraft_v1.R;
import com.example.starvdraft_v1.adapters.IngredientAdapter;
import com.example.starvdraft_v1.adapters.PrepStepAdapter;
import com.example.starvdraft_v1.extras.KeyBoardControl;
import com.example.starvdraft_v1.extras.TextControl;
import com.example.starvdraft_v1.models.Ingredient;
import com.example.starvdraft_v1.models.PrepStep;
import com.example.starvdraft_v1.models.Recipe;
import com.example.starvdraft_v1.network.ApiUtils;
import com.example.starvdraft_v1.network.requests.AddRecipe;
import com.example.starvdraft_v1.network.requests.AddRecipeDraft;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public class CreateRecipeActivity extends AppCompatActivity {

    EditText et_RecipeName, et_IName, et_IQuantity, et_IComment, et_PrepStep, et_Portion, et_TimeP, et_TimeC;
    Spinner sp_IUnits, sp_TimeP, sp_TimeC;
    ImageView iv_RecipeAvatar;
    Button btn_NewIngredient, btn_NewPrepStep, btn_RecipeDraft, btn_RecipeComplete;
    ToggleButton btn_Breakfast, btn_Lunch, btn_Dinner, btn_Snack, btn_Salad, btn_Pasta, btn_Soup, btn_Meat, btn_Healthy;
    ArrayAdapter<CharSequence> adapter_IUnits, adapter_TimeP, adapter_TimeC;
    String selectedIUnit, selectedTimeP, selectedTimeC, formattedIngredients=" ", formattedPrepSteps=" ", timeP, timeC, selectedCategorys="";
    RecyclerView recyclerIngredients, recyclerPrepSteps;
    IngredientAdapter ingredientAdapter;
    PrepStepAdapter prepStepAdapter;
    ArrayList<PrepStep> prepSteps;
    ArrayList<Ingredient> ingredients;
    ImageView ivAnimation;
    AnimationDrawable animation;
    AddRecipe addRecipe;
    AddRecipeDraft addRecipeDraft;

    private String imageByteArray;
    private Bitmap bitmap;
    private static int RESULT_LOAD_IMAGE = 9;
    final int PERMISSION_READ_STORAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            /*Comparar con startActivityForResult*/
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_READ_STORAGE);
        }

        et_RecipeName = (EditText) findViewById(R.id.et_RecipeName);
        et_IName = (EditText) findViewById(R.id.et_IName);
        et_IQuantity = (EditText) findViewById(R.id.et_IQuantity);
        et_IComment = (EditText) findViewById(R.id.et_IComment);
        et_PrepStep = (EditText) findViewById(R.id.et_PrepStep);
        et_Portion = (EditText) findViewById(R.id.et_Portion);
        et_TimeP = (EditText) findViewById(R.id.et_TimeP);
        et_TimeC = (EditText) findViewById(R.id.et_TimeC);
        sp_IUnits = (Spinner) findViewById(R.id.sp_IUnits);
        sp_TimeP = (Spinner) findViewById(R.id.sp_TimeP);
        sp_TimeC = (Spinner) findViewById(R.id.sp_TimeC);
        iv_RecipeAvatar = (ImageView) findViewById(R.id.iv_RecipeAvatar);
        btn_NewIngredient = (Button) findViewById(R.id.btn_NewIngredient);
        btn_NewPrepStep = (Button) findViewById(R.id.btn_NewPrepStep);
        btn_RecipeDraft = (Button) findViewById(R.id.btn_RecipeDraft);
        btn_RecipeComplete = (Button) findViewById(R.id.btn_RecipeComplete);
        btn_Breakfast = (ToggleButton) findViewById(R.id.btn_Breakfast);
        btn_Lunch = (ToggleButton) findViewById(R.id.btn_Lunch);
        btn_Dinner = (ToggleButton) findViewById(R.id.btn_Dinner);
        btn_Snack = (ToggleButton) findViewById(R.id.btn_Snack);
        btn_Salad = (ToggleButton) findViewById(R.id.btn_Salad);
        btn_Pasta = (ToggleButton) findViewById(R.id.btn_Pasta);
        btn_Soup = (ToggleButton) findViewById(R.id.btn_Soup);
        btn_Meat = (ToggleButton) findViewById(R.id.btn_Meat);
        btn_Healthy = (ToggleButton) findViewById(R.id.btn_Healthy);
        recyclerIngredients = (RecyclerView) findViewById(R.id.recyclerIngredients);
        recyclerPrepSteps = (RecyclerView) findViewById(R.id.recyclerPrepSteps);
        ivAnimation= (ImageView) findViewById(R.id.ivAnimation);

        et_IComment.setText(" ");

        ingredients= new ArrayList<>();
        prepSteps = new ArrayList<>();

        addRecipe = ApiUtils.addRecipe();
        addRecipeDraft = ApiUtils.addRecipeDraft();

        recyclerIngredients.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerIngredients.setLayoutManager(linearLayoutManager);

        recyclerPrepSteps.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerPrepSteps.setLayoutManager(linearLayoutManager2);

        ivAnimation.setBackgroundResource(R.drawable.animation_nyan);
        animation= (AnimationDrawable) ivAnimation.getBackground();

        adapter_IUnits = ArrayAdapter.createFromResource(CreateRecipeActivity.this,R.array.array_Units, android.R.layout.simple_spinner_item);
        adapter_IUnits.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_IUnits.setAdapter(adapter_IUnits);

        adapter_TimeP = ArrayAdapter.createFromResource(CreateRecipeActivity.this,R.array.array_Time, android.R.layout.simple_spinner_item);
        adapter_TimeP.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_TimeP.setAdapter(adapter_TimeP);

        adapter_TimeC = ArrayAdapter.createFromResource(CreateRecipeActivity.this,R.array.array_Time, android.R.layout.simple_spinner_item);
        adapter_TimeC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_TimeC.setAdapter(adapter_TimeC);


        sp_IUnits.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedIUnit= String.valueOf(adapter_IUnits.getItem(position));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        sp_TimeP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTimeP= String.valueOf(adapter_TimeP.getItem(position));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        sp_TimeC.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTimeC= String.valueOf(adapter_TimeC.getItem(position));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        iv_RecipeAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,RESULT_LOAD_IMAGE);

            }
        });


        btn_NewIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyBoardControl.hideKeyboard(CreateRecipeActivity.this,getCurrentFocus());
                EditText[] ingredientEntries = {et_IName, et_IQuantity};
                EditText[] ingredientCompleteEntries = {et_IName, et_IQuantity, et_IComment};

                if (TextControl.fieldsNotEmpty(ingredientEntries,getString(R.string.error_data_required))) {
                    String[] ingredientData = TextControl.getTextFromFields(ingredientCompleteEntries);
                    Ingredient ingredient= new Ingredient(ingredientData[0], ingredientData[1], selectedIUnit, ingredientData[2]);
                    ingredients.add(ingredient);

                    for (EditText entry:ingredientCompleteEntries){
                        entry.setText(" ");
                    }
                    showAllIngredients();
                }
            }
        });


        btn_NewPrepStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyBoardControl.hideKeyboard(CreateRecipeActivity.this,getCurrentFocus());
                EditText[] ingredientPrepStep = {et_PrepStep};

                if (TextControl.fieldsNotEmpty(ingredientPrepStep,getString(R.string.error_data_required))) {
                    String[] prepStepText = TextControl.getTextFromFields(ingredientPrepStep);
                    PrepStep prepStep= new PrepStep(prepStepText[0]);
                    prepSteps.add(prepStep);

                    for (EditText entry:ingredientPrepStep){
                        entry.setText("");
                    }
                    showAllPrepSteps();
                }
            }
        });

        
        btn_RecipeDraft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!ingredients.isEmpty()){
                    if (!prepSteps.isEmpty()){
                        KeyBoardControl.hideKeyboard(CreateRecipeActivity.this,getCurrentFocus());
                        EditText[] recipeEntries = {et_RecipeName, et_Portion, et_TimeP, et_TimeC};

                        if (TextControl.fieldsNotEmpty(recipeEntries,getString(R.string.error_data_required))){

                            String[] recipeData = TextControl.getTextFromFields(recipeEntries);

                            for (Ingredient ingredient : ingredients){
                                formattedIngredients = formattedIngredients + ingredient.getiName() + ":" + ingredient.getiQuantity() + ":" + ingredient.getiUnits() + ":" + ingredient.getiComment() + "::";
                            }

                            for (PrepStep prepStep : prepSteps){
                                formattedPrepSteps = formattedPrepSteps + prepStep.getPrepStep() + "::";
                            }

                            timeP = recipeData[2] + selectedTimeP;
                            timeC = recipeData[3] + selectedTimeC;

                            if(btn_Breakfast.isChecked()){
                                selectedCategorys = selectedCategorys + "Desayuno::";
                            }
                            if(btn_Lunch.isChecked()){
                                selectedCategorys = selectedCategorys + "Comida::";
                            }
                            if(btn_Dinner.isChecked()){
                                selectedCategorys = selectedCategorys + "Cena::";
                            }
                            if(btn_Snack.isChecked()){
                                selectedCategorys = selectedCategorys + "Colaciones::";
                            }
                            if(btn_Salad.isChecked()){
                                selectedCategorys = selectedCategorys + "Ensaladas::";
                            }
                            if(btn_Pasta.isChecked()){
                                selectedCategorys = selectedCategorys + "Pastas::";
                            }
                            if(btn_Soup.isChecked()){
                                selectedCategorys = selectedCategorys + "Sopas::";
                            }
                            if(btn_Meat.isChecked()){
                                selectedCategorys = selectedCategorys + "Carnes::";
                            }
                            if(btn_Healthy.isChecked()){
                                selectedCategorys = selectedCategorys + "Saludable::";
                            }

                            //Log.i("RestServiceCreate", selectedCategorys);
                            addNewRecipeDraft(recipeData[0],recipeData[1],timeP,timeC,formattedPrepSteps,formattedIngredients,"1","5",imageByteArray, selectedCategorys);

                        }
                    } else {
                        Toast.makeText(CreateRecipeActivity.this,"Pasos faltantes",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CreateRecipeActivity.this,"Ingredientes faltantes",Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(CreateRecipeActivity.this,"Guardado en tus borradores",Toast.LENGTH_LONG).show();
                selectedCategorys = "";
            }
        });


        btn_RecipeComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!ingredients.isEmpty()){
                    if (!prepSteps.isEmpty()){
                        KeyBoardControl.hideKeyboard(CreateRecipeActivity.this,getCurrentFocus());
                        EditText[] recipeEntries = {et_RecipeName, et_Portion, et_TimeP, et_TimeC};

                        if (TextControl.fieldsNotEmpty(recipeEntries,getString(R.string.error_data_required))){

                            String[] recipeData = TextControl.getTextFromFields(recipeEntries);

                            for (Ingredient ingredient : ingredients){
                                formattedIngredients = formattedIngredients + ingredient.getiName() + ":" + ingredient.getiQuantity() + ":" + ingredient.getiUnits() + ":" + ingredient.getiComment() + "::";
                            }

                            for (PrepStep prepStep : prepSteps){
                                formattedPrepSteps = formattedPrepSteps + prepStep.getPrepStep() + "::";
                            }

                            timeP = recipeData[2] + selectedTimeP;
                            timeC = recipeData[3] + selectedTimeC;

                            if(btn_Breakfast.isChecked()){
                                selectedCategorys = selectedCategorys + "Desayuno::";
                            }
                            if(btn_Lunch.isChecked()){
                                selectedCategorys = selectedCategorys + "Comida::";
                            }
                            if(btn_Dinner.isChecked()){
                                selectedCategorys = selectedCategorys + "Cena::";
                            }
                            if(btn_Snack.isChecked()){
                                selectedCategorys = selectedCategorys + "Colaciones::";
                            }
                            if(btn_Salad.isChecked()){
                                selectedCategorys = selectedCategorys + "Ensaladas::";
                            }
                            if(btn_Pasta.isChecked()){
                                selectedCategorys = selectedCategorys + "Pastas::";
                            }
                            if(btn_Soup.isChecked()){
                                selectedCategorys = selectedCategorys + "Sopas::";
                            }
                            if(btn_Meat.isChecked()){
                                selectedCategorys = selectedCategorys + "Carnes::";
                            }
                            if(btn_Healthy.isChecked()){
                                selectedCategorys = selectedCategorys + "Saludable::";
                            }

                            Log.i("RestService", "Working..");
                            addNewRecipe(recipeData[0],recipeData[1],timeP,timeC,formattedPrepSteps,formattedIngredients,"1","5",imageByteArray, selectedCategorys);

                        }
                    } else {
                        Toast.makeText(CreateRecipeActivity.this,"Pasos faltantes",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CreateRecipeActivity.this,"Ingredientes faltantes",Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    //------------------------------------------------------------------------------------------------------------------------------------------------
    //AFUERA de OnCreate
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();

            try{
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),selectedImage);
                imageByteArray = imageToString();
                iv_RecipeAvatar.setImageBitmap(bitmap);

            }catch (IOException e){
                e.printStackTrace();
            }

        }
    }


    //Método para pasar los datos de nuestro StringArray Weathers a nuestro adaptador para poder trabajarlos junto con el holder
    private void showAllIngredients(){
        ingredientAdapter = new IngredientAdapter(ingredients);
        recyclerIngredients.setAdapter(ingredientAdapter);
    }

    private void showAllPrepSteps(){
        prepStepAdapter = new PrepStepAdapter(prepSteps);
        recyclerPrepSteps.setAdapter(prepStepAdapter);
    }

    private void clearRecipeScreen(){
        recyclerPrepSteps.removeAllViewsInLayout();
        prepSteps.clear();
        recyclerIngredients.removeAllViewsInLayout();
        ingredients.clear();
        et_IName.setText("");
        et_Portion.setText("");
        et_TimeP.setText("");
        et_TimeC.setText("");
        animation.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                animation.stop();
            }
        },2300);

    }

    private String imageToString (){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
        byte[] selectedImageByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(selectedImageByte,Base64.DEFAULT);
    }

    public void addNewRecipe(String recipe, String portion, String timeP, String timeC, String preparation, String ingredients, String author, String grade, String snapshot, String category) {
        addRecipe.saveRecipe(recipe, portion, timeP, timeC, preparation, ingredients, author, grade, snapshot, category).enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {

                if(response.isSuccessful()) {
                    Log.i("RestService", "SUCCESS" + response.body().toString());
                    Log.i("RestService", call.request().toString());
                    clearRecipeScreen();
                    Toast.makeText(CreateRecipeActivity.this,"Receta agregada con éxito",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Recipe> call, Throwable t) {
                Log.i("RestService", String.valueOf(t));
                Toast.makeText(CreateRecipeActivity.this,"Error al agregar receta",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addNewRecipeDraft(String recipe, String portion, String timeP, String timeC, String preparation, String ingredients, String author, String grade, String snapshot, String category) {
        addRecipeDraft.saveRecipeDraft(recipe, portion, timeP, timeC, preparation, ingredients, author, grade, snapshot, category).enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {

                if(response.isSuccessful()) {
                    Log.i("RestService", "SUCCESS" + response.body().toString());
                    Log.i("RestService", call.request().toString());
                    clearRecipeScreen();
                    Toast.makeText(CreateRecipeActivity.this,"Borrador guardado con éxito",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Recipe> call, Throwable t) {
                Log.i("RestService", String.valueOf(t));
                Toast.makeText(CreateRecipeActivity.this,"Error al guardar borrador",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
