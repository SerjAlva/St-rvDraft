package com.example.starvdraft_v1.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.starvdraft_v1.R;
import com.example.starvdraft_v1.adapters.DishItemAdapter;
import com.example.starvdraft_v1.holders.DishItemViewHolder;
import com.example.starvdraft_v1.models.DishItem;
import com.example.starvdraft_v1.models.Recipe;
import com.example.starvdraft_v1.network.ApiUtils;
import com.example.starvdraft_v1.network.requests.GetDishList;
import com.example.starvdraft_v1.preferences.AppPreferences;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DishListActivity extends AppCompatActivity {

    //Creamos variables de referencia
    RecyclerView recyclerView;
    //Creamos un adaptador y un LinearLayoutManager que son necesarios para usar nuestro recyclerView
    LinearLayoutManager linearLayoutManager;
    DishItemAdapter dishItemAdapter;
    Button btnComprar;
    ArrayList<DishItem> lista;
    ArrayList<Recipe> dishList;
    GetDishList getDishList;
    String formattedIngredients="";
    AppPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_list);

        //Realizamos la carga de datos al iniciar la actividad
        getDishList = ApiUtils.getDishList();
        dishList = new ArrayList<>();
        lista = new ArrayList<>();
        prefs = new AppPreferences(DishListActivity.this);
        getDishList(prefs.getUserDishList());

        //Referenciamos a las variables con los elementos de nuestro xml
        recyclerView = (RecyclerView) findViewById(R.id.rvRecyclerView);
        btnComprar = findViewById(R.id.btnComprar);

        //Inicializamos a linearLayoutManager y lo asignamos a nuestro recyclerView

        recyclerView.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        dishItemAdapter = new DishItemAdapter(lista);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(dishItemAdapter);


        /*dishItemAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onAddPortionClick(int position) {
                int porciones = Integer.parseInt(lista.get(position).porciones);
                lista.get(position).porciones = String.valueOf(porciones + 1);
                lista.get(position).precioMostrador = lista.get(position).precio*porciones;
                dishItemAdapter.notifyItemChanged(position);
            }

            @Override
            public void onRetirePortionClick(int position) {
                int porciones = Integer.parseInt(lista.get(position).porciones);
                if(porciones == 1){
                    Toast.makeText(DishListActivity.this, getResources().getString(R.string.retirar_porcion_error), Toast.LENGTH_SHORT).show();
                }else{
                    lista.get(position).porciones = String.valueOf(porciones-1);
                    lista.get(position).precioMostrador = lista.get(position).precio*porciones;
                    dishItemAdapter.notifyItemChanged(position);
                }
            }

            @Override
            public void onRemoveClick(int position) {
                String pregunta = getString(R.string.onRemove1);
                AlertDialog.Builder builder = new AlertDialog.Builder(DishListActivity.this);
                builder.setMessage(pregunta + lista.get(position).titulo + "?")
                        .setTitle(R.string.dialog_title)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                lista.remove(position);
                                dishItemAdapter.notifyItemRemoved(position);
                            }
                        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                //AlertDialog dialog = builder.create();
                try {
                    builder.show();
                }catch (Exception e){
                    Toast.makeText(DishListActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });*/

        btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Obtenemos lista de ingredientes
                formattedIngredients = "";
                for(DishItem dishItem : lista){
                    formattedIngredients = formattedIngredients + dishItem.getIngredientes();
                }
                Log.i("ExtraInfo",formattedIngredients);
                Intent intent = new Intent(DishListActivity.this, MarketListActivity.class)
                        .putExtra("lista", formattedIngredients);
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
                        DishItem dishItem = new DishItem();
                        dishItem.setTitulo(encodedRecipe.getReceta());
                        dishItem.setCategoria("Carnes");
                        dishItem.setIngredientes(encodedRecipe.getIngredientesD());
                        dishItem.setPorciones(encodedRecipe.getPorcion());
                        dishItem.setPrecio(200);
                        lista.add(dishItem);
                    }
                    showAllDishes();
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.i("RestServiceSuggestions", String.valueOf(t));
            }
        });
    }

    private void showAllDishes(){
        dishItemAdapter = new DishItemAdapter(lista);
        recyclerView.setAdapter(dishItemAdapter);
    }

    //Esta clase sirve para controlar los botones de cada card
    public interface OnItemClickListener{
        //Método que se manda a llamar cuando se toca en añadir porción
        void onAddPortionClick(int position);
        //Método que se manda a llamar cuando se toca en quitar porción
        void onRetirePortionClick(int position);
        //Método que se llama cuando se toca el botón eliminar platillo
        void onRemoveClick(int position);
    }

}
