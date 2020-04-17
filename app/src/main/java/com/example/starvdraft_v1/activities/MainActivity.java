package com.example.starvdraft_v1.activities;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.starvdraft_v1.R;
import com.example.starvdraft_v1.extras.KeyBoardControl;
import com.example.starvdraft_v1.extras.TextControl;
import com.example.starvdraft_v1.models.User;
import com.example.starvdraft_v1.network.ApiUtils;
import com.example.starvdraft_v1.network.requests.GetUser;
import com.example.starvdraft_v1.preferences.AppPreferences;

public class MainActivity extends AppCompatActivity {

    //--------------------------------------------------------------------------------------------------------------------------
    /* 1.0 Declaramos nuestras variables*/

    EditText et_UserName, et_UserPassword;
    Switch sRemember;
    Button btn_LogIn, btn_SignUp;
    AppPreferences prefs;
    GetUser getUser;

    final int REGISTER_REQUEST= 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_UserName = (EditText) findViewById(R.id.et_UserName);
        et_UserPassword = (EditText) findViewById(R.id.et_UserPassword);
        sRemember = (Switch) findViewById(R.id.sRemember);
        btn_LogIn = (Button) findViewById(R.id.btn_LogIn);
        btn_SignUp = (Button) findViewById(R.id.btn_SignUp);

        getUser = ApiUtils.getUser();

        prefs = new AppPreferences(MainActivity.this);

        /* 1.1 Buscamos dentro de nuestro AppPreferences el estado de LogIn para hacerlo automaticamente*/
        if (prefs.isLogued()){
            logIn();
        }

        //-----------------------------------------------------------------------------------------------------------------------
        /* 2 Configuramos la acción de nuestro btnSignUp con onClickListener*/

        btn_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivityForResult(intent,REGISTER_REQUEST);
            }
        });

        //-----------------------------------------------------------------------------------------------------------------------
        /* 3 Configuramos la acción de nuestro btnLogIn con onClickListener*/

        btn_LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyBoardControl.hideKeyboard(MainActivity.this,getCurrentFocus());
                //Declaramos un arreglo de EditTexts con los EditText que vamos a necesitar para hacer nuestro LogIn
                EditText[] fields = {et_UserName,et_UserPassword};
                if(TextControl.fieldsNotEmpty(fields,getString(R.string.error_data_required))){
                    //Creamos un arreglo de String para recuperar los datos de nuestro arreglo de ET
                    String[] texts = TextControl.getTextFromFields(fields);
                    //Creamos un IF para poder asegurarnos de que el nombre de usuario y la constraseña coinciden con lo que
                    //tenemos en nuestro AppPreferences
                    getUser(texts[0],texts[1]);

                    /*
                    if (texts[0].equals(prefs.getUserName()) && texts[1].equals(prefs.getUserPassword())){
                        for (EditText field:fields){
                            field.setText("");
                        }


                        prefs.setLoginStatus(sRemember.isChecked());
                        //Mandamos a llamar nuestro método logIn()
                        getUser(texts[0],texts[1]);
                    }else{
                        Toast.makeText(MainActivity.this,getString(R.string.error_user_password),Toast.LENGTH_LONG).show();
                    }
                    */
                }
            }
        });
    }


    //FUERA DE onCreate!!
    //-------------------------------------------------------------------------------------------------------------------------
    /* 4 Creamos nuestro método logIn*/

    private void logIn(){
        //Declaramos nuestro Intent para cambia de actividad haciendo uso de Flags
        Intent intent= new Intent(MainActivity.this,PassActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    //-------------------------------------------------------------------------------------------------------------------------
    /* 5 Mandamos a llamar el método onActivityResult*/

    //Obtenemos los datos que nos manda la actividad de registro al ser concluido este
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REGISTER_REQUEST:
                if (resultCode == RESULT_OK) {
                    try {
                        et_UserName.setText(data.getExtras().getString("email"));
                    } catch (NullPointerException e) {
                        e.getLocalizedMessage();
                    }
                }
                break;
        }
    }

    //-------------------------------------------------------------------------------------------------------------------------

    /*Pedimos información de usuario en BD*/

    public void getUser(String correo, String psswd) {
        getUser.getUser(correo, psswd).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()) {
                    //Log.i("RestServiceLogIn", "SUCCESS" + response.body().toString());
                    Log.i("RestServiceLogIn", call.request().toString());
                    User user = response.body();
                    if(user.getNombre().equals("Error")){
                        Toast.makeText(MainActivity.this,"Cuenta inexistente",Toast.LENGTH_SHORT).show();
                    }else{
                        if (et_UserName.getText().toString().equals(user.getCorreo())){
                            if(et_UserPassword.getText().toString().equals(user.getPsswd())){
                                logIn();
                            }else{
                                Toast.makeText(MainActivity.this,"Contraseña incorrecta",Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(MainActivity.this,"Cuenta inexistente",Toast.LENGTH_SHORT).show();
                            Log.i("RestServiceLogIn", et_UserName.getText().toString() + " - " + user.getCorreo());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.i("RestServiceFailure", String.valueOf(t));
                Log.i("RestService", call.request().toString());
            }
        });
    }
}
