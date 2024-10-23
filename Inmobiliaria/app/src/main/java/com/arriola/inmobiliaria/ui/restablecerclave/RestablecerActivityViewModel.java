package com.arriola.inmobiliaria.ui.restablecerclave;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.arriola.inmobiliaria.model.ResponseApi;
import com.arriola.inmobiliaria.request.ApiClient;
import com.arriola.inmobiliaria.ui.login.MainActivity;
import com.arriola.inmobiliaria.ui.menu.MenuActivity;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestablecerActivityViewModel extends AndroidViewModel {
    private Context context;
    public RestablecerActivityViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public void cambiarClave(String token, String clave){
        Gson gson = new Gson();
        Call< ResponseApi> responseApiCall = ApiClient.getApiInmobiliaria().cambiarClave("Bearer " + token, gson.toJson(clave));
        responseApiCall.enqueue(new Callback<ResponseApi>() {
            @Override
            public void onResponse(Call<ResponseApi> call, Response<ResponseApi> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context, "Se actualizo correctamente, la contraseña", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                else if(response.code() == 401){
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<ResponseApi> call, Throwable throwable) {

            }
        });
    }
}
