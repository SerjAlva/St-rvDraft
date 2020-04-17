package com.example.starvdraft_v1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.starvdraft_v1.R;
import com.example.starvdraft_v1.extras.KeyBoardControl;
import com.example.starvdraft_v1.extras.TextControl;
import com.example.starvdraft_v1.preferences.AppPreferences;

public class SearchActivity extends AppCompatActivity {

    /**Creamos nuestras vistas en pantalla como variables**/
    EditText ET_Campo;
    ListView LV_Lista;

    ArrayAdapter<String> AA_adaptador;

    String ingredientes[] = {"Almendra", "Almeja", "Arroz", "Harina", "Betabel", "Berenjena", "Coco", "Cotorro", "Alubias", "Cajeta", "Cajun", "Caldo"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        /**Referenciamos las variables creadas, a los elementos en pantalla. Usamos el ID**/
        ET_Campo = (EditText) findViewById(R.id.ET_Campo);
        LV_Lista = (ListView) findViewById(R.id.LV_Lista);

        /**Configuramos el funcionamiento de nuestro adaptador [contexto,visualización,elementos a mostrar]**/
        AA_adaptador = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, ingredientes);
        LV_Lista.setAdapter(AA_adaptador); //Es necesario pasarle la configuración de nuestro adaptador a nuestro ListView

        /**Con TextWatcher, monitoreamos los cambios en el EditText que seleccionamos**/
        ET_Campo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                AA_adaptador.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }
}
