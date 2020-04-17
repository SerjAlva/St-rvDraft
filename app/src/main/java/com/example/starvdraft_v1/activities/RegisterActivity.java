package com.example.starvdraft_v1.activities;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.starvdraft_v1.R;
import com.example.starvdraft_v1.extras.KeyBoardControl;
import com.example.starvdraft_v1.extras.TextControl;
import com.example.starvdraft_v1.models.User;
import com.example.starvdraft_v1.network.ApiUtils;
import com.example.starvdraft_v1.network.requests.RegisterUser;
import com.example.starvdraft_v1.preferences.AppPreferences;

public class RegisterActivity extends AppCompatActivity {

    //--------------------------------------------------------------------------------------------------------------------------

    /* 1.0 Declaramos nuestra variables*/
    EditText etName, etLastName, etAge, etEmail, etAvatar, etPassword, etPasswordConfirm;
    Spinner spGender;
    Button btnRegister;
    AppPreferences prefs;
    String selectedGender;
    ArrayAdapter<CharSequence> adapter;
    RegisterUser registerUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        /* 1.1 Declaramos nuesras variables*/
        etName = (EditText) findViewById(R.id.etName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etAge = (EditText) findViewById(R.id.etAge);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etAvatar = (EditText) findViewById(R.id.etAvatar);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etPasswordConfirm = (EditText) findViewById(R.id.etPasswordConfirm);
        spGender = (Spinner) findViewById(R.id.spGender);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        prefs = new AppPreferences(RegisterActivity.this);

        registerUser = ApiUtils.registerUser();


        /* 2 Creamos adaptador para nuestro Spinner*/
        adapter = ArrayAdapter.createFromResource(RegisterActivity.this,R.array.array_Gender, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGender.setAdapter(adapter);


        /* 3 Creamos onItemSelectedListener para nuestro Spinner*/
        spGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Tomamos la selección de nuestro Spinner y guardamos el valor del String en otra variable
                selectedGender= String.valueOf(adapter.getItem(position));
                //Mostramos la opcion seleccionada en un Toast
                Toast.makeText(getApplicationContext(),selectedGender,Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        /* 4 Configuramos la acción de nuestro btnRegister con onClickListener*/
        //En este métodó es en donde mandaremos nuestra información a appPreferences
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                KeyBoardControl.hideKeyboard(RegisterActivity.this,getCurrentFocus());
                EditText[] entries = {etName, etLastName, etAge, etEmail, etAvatar, etPassword, etPasswordConfirm};

                if (TextControl.fieldsNotEmpty(entries,getString(R.string.error_data_required))){

                    String[] texts = TextControl.getTextFromFields(entries);
                    if (TextControl.passwordsMatch(texts[5],texts[6])){

                        for (EditText entry:entries){
                            entry.setText("");
                        }

                        prefs.setUserName(texts[0]);
                        prefs.setUserLastName(texts[1]);
                        prefs.setUserEmail(texts[3]);
                        prefs.setUserPassword(texts[5]);
                        prefs.setUserGender(selectedGender);
                        prefs.setUserAge(texts[2]);
                        prefs.setUserAvatar(texts[4]);
                        Toast.makeText(RegisterActivity.this,getString(R.string.label_user_registered),Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                        intent.putExtra("email",texts[3]);
                        setResult(RESULT_OK,intent);
                        saveNewUser(texts[0], texts[1], Integer.valueOf(texts[2]), selectedGender, texts[3], texts[5], texts[4]);
                        finish();

                    }else{
                        Toast.makeText(RegisterActivity.this,getString(R.string.error_password),Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    //-----------------------------------------------------------------------------------------------------------------------------
    //FUERA DE OnCreate

    public void saveNewUser(String nombre, String apellido, Integer edad, String genero, String correo, String psswd, String avatar) {
        registerUser.saveUser(nombre, apellido, edad, genero, correo, psswd, avatar).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if(response.isSuccessful()) {
                    Log.i("RestService", "SUCCESS" + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("RestService", String.valueOf(t));
            }
        });
    }


}
