package com.arriola.inmobiliaria.ui.login;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.arriola.inmobiliaria.model.LoginView;
import com.arriola.inmobiliaria.model.ResponseApi;
import com.arriola.inmobiliaria.model.Token;
import com.arriola.inmobiliaria.request.ApiClient;
import com.arriola.inmobiliaria.ui.menu.MenuActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityViewModel extends AndroidViewModel {
    private Context context;
    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();

    }

    public void entrar(String usuario, String clave){
        Call<Token> llamada = ApiClient.getApiInmobiliaria().login(new LoginView(usuario, clave));

        llamada.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                Token token = response.body();
                if(response.isSuccessful() && token != null) {
                    ApiClient.Guardar(context, token);
                    Intent intent = new Intent(context, MenuActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                else{
                    Toast.makeText(context, "Usuario y/o Clave incorrectas", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable throwable) {
                Toast.makeText(context, "Fallo la llamada", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void EnviarMail(String usuario){
        if(usuario != null && !usuario.isEmpty()){
            Call<ResponseApi> responseApiCall = ApiClient.getApiInmobiliaria().mailRecupero(usuario);

            responseApiCall.enqueue(new Callback<ResponseApi>() {
                @Override
                public void onResponse(Call<ResponseApi> call, Response<ResponseApi> response) {
                    ResponseApi responseApi = response.body();
                    if(response.isSuccessful() && responseApi != null){
                        Toast.makeText(context, "Email enviado, revise su bandeja de correo.", Toast.LENGTH_SHORT).show();
                    }
                    else if(response.code() == 401){
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                    else{
                        Toast.makeText(context, "Error onResponse: al envio de email", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseApi> call, Throwable throwable) {
                    Toast.makeText(context, "Error onFailure", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{

            Toast.makeText(context, "Ingrese el usuario!", Toast.LENGTH_SHORT).show();
        }
    }
}
