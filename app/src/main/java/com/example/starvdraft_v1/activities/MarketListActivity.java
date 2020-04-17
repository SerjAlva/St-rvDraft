package com.example.starvdraft_v1.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.starvdraft_v1.R;
import com.example.starvdraft_v1.adapters.CategoryAdapter;
import com.example.starvdraft_v1.models.FoodCategory;
import com.example.starvdraft_v1.models.MarketListItem;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MarketListActivity extends AppCompatActivity {
    RecyclerView rvContenedor;
    ImageView ivComprar, ivAñadir;
    ArrayList<FoodCategory> categorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_list);
        Intent intent = getIntent();
        String lista = intent.getExtras().getString("lista");
        categorias = getInfo(lista);

        //categorias = generarCategorias();
        //categorias = getInfo("{Zahanhoria:1.5:kg:Frito::Calabacines:3.5:kg:Frutas y Verduras;Calabacitas:1.0:kg:Frutas y Verduras;pechuga de pavo:350.0:g:Salchichonería;frijoles bayos:850.0:g:Despensa;arroz integral:900.0:g:Despensa;frijoles negros:600.0:g:Despensa;papel higiénico:20.0:rollos:Otros;aceite:200.0:ml:Básicos;sal:1.0:pizca:Básicos;pimienta:1.0:pizca:Básicos;}");
        categorias = getInfo(getIntent().getExtras().getString("lista"));
        rvContenedor = (RecyclerView) findViewById(R.id.rvContenedor);
        ivAñadir = findViewById(R.id.ivAñadir);
        ivComprar = findViewById(R.id.ivComprar);

        CategoryAdapter adapter = new CategoryAdapter(categorias);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MarketListActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvContenedor.setLayoutManager(linearLayoutManager);
        rvContenedor.setAdapter(new CategoryAdapter(categorias));
        rvContenedor.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy>0){
                    ivAñadir.setVisibility(View.GONE);
                    ivComprar.setVisibility(View.GONE);

                }else{
                    ivAñadir.setVisibility(View.VISIBLE);
                    ivComprar.setVisibility(View.VISIBLE);
                }
            }
        });

        ivAñadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MarketListActivity.this, "añadiendo platillo :p", Toast.LENGTH_SHORT).show();
            }
        });

        ivComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MarketListActivity.this, getResources().getString(R.string.imprimiendoLista), Toast.LENGTH_SHORT).show();
                try{
                    File ruta = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                    File listasGuardadas = new File(ruta, "Listas Guardadas");
                    if(!listasGuardadas.exists()){
                        listasGuardadas.mkdir();
                    }
                    try{
                        File nuevaLista = new File(listasGuardadas, "Mi_Lista.txt");
                        nuevaLista.createNewFile();
                        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(nuevaLista));
                        for(FoodCategory category : categorias){
                            for(MarketListItem item : category.getElementos()){
                                bufferedWriter.write(item.toString());
                            }
                        }
                        bufferedWriter.close();
                    }catch (Exception ex){
                        Log.e("error", ex.getMessage());
                    }
                }catch(Exception ex){
                    Log.e("error", ex.getMessage());
                }
            }
        });
    }

    public ArrayList<MarketListItem> generarItemsFyV(){
        ArrayList<MarketListItem> lista = new ArrayList<>();
        lista.add(new MarketListItem(1.5, "kg", "Zahanhoria"));
        lista.add(new MarketListItem(3.5, "kg", "Calabacines"));
        lista.add(new MarketListItem(1, "kg", "Calabacitas"));
        return lista;
    }

    public ArrayList<MarketListItem> generarItemsSalch() {
        ArrayList<MarketListItem> lista = new ArrayList<>();
        lista.add(new MarketListItem(350, "g", "pechuga de pavo"));
        return lista;
    }

    public ArrayList<MarketListItem> generarItemsDespensa() {
        ArrayList<MarketListItem> lista = new ArrayList<>();
        lista.add(new MarketListItem(850, "g" , "frijoles bayos"));
        lista.add(new MarketListItem(900, "g", "arroz integral"));
        lista.add(new MarketListItem(600, "g", "frijoles negros"));
        return lista;
    }

    public ArrayList<MarketListItem> generarItemsOtros() {
        ArrayList<MarketListItem> lista = new ArrayList<>();
        lista.add(new MarketListItem(20, "rollos", "papel higiénico"));
        return lista;
    }

    public ArrayList<MarketListItem> generarItemsBasicos(){
        ArrayList<MarketListItem> lista = new ArrayList<>();
        lista.add(new MarketListItem(200, "ml", "aceite"));
        lista.add(new MarketListItem(1, "pizca", "sal"));
        lista.add(new MarketListItem(1, "pizca", "pimienta"));
        return lista;
    }


    public ArrayList<FoodCategory> generarCategorias(){
        ArrayList<FoodCategory> lista = new ArrayList<>();
        lista.add(new FoodCategory("Frutas y Verduras", R.color.FyV, generarItemsFyV()));
        lista.add(new FoodCategory("Salchichonería", R.color.salch, generarItemsSalch()));
        lista.add(new FoodCategory("Despensa", R.color.desp, generarItemsDespensa()));
        lista.add(new FoodCategory("Otros", R.color.otros, generarItemsOtros()));
        lista.add(new FoodCategory("Básicos", R.color.basicos, generarItemsBasicos()));
        return lista;
    }


    public String generarLista(){
        String lista = "";
        String separador = ":";
        for(FoodCategory categoria : categorias){
            for(MarketListItem elemento : categoria.getElementos()){
                lista += elemento.getNombreIngrediente() + separador + elemento.getCantidad() + separador + elemento.getUnidad() + separador + categoria.getTitulo() + ";";
            }
        }
        //lista += "}";
        Log.e("aqui", lista);
        return lista;
    }



    public ArrayList<FoodCategory> getInfo(String rawInfo){

        ArrayList<MarketListItem> informacionObtenida = new ArrayList<>();
        ArrayList<FoodCategory> lista = new ArrayList<>();
        String nombre, unidad, categoria;
        Double cantidad;

        String[] elementos = rawInfo.split("::");
        for(String elemento : elementos){
            String[] infoElemento = elemento.split(":");
            /*for(String info : infoElemento){
                Log.e("infoElemento", info);
            }*/
            if(infoElemento.length == 4){
                Log.e("longitud", Integer.toString(infoElemento.length));
                nombre = infoElemento[0];
                cantidad = Double.parseDouble(infoElemento[1]);
                unidad = infoElemento[2];
                categoria = infoElemento[3];
                informacionObtenida.add(new MarketListItem(cantidad, unidad, nombre));
            }
        }
        lista.add(new FoodCategory("Lista de compras", R.color.desp, informacionObtenida));
        return  lista;
    }

}
