package com.arriola.inmobiliaria.ui.menu.ui.cambiarclave;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.arriola.inmobiliaria.model.ResponseApi;
import com.arriola.inmobiliaria.request.ApiClient;
import com.arriola.inmobiliaria.ui.login.MainActivity;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CambiarClaveFragmentViewModel extends AndroidViewModel {
    private Context context;
    public CambiarClaveFragmentViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public void CambiarClave(String clave){
        Gson gson = new Gson();

        Call<ResponseApi> responseApiCall = ApiClient.getApiInmobiliaria().cambiarClave(ApiClient.getToken(context).getTokenHeader(), gson.toJson(clave));

        responseApiCall.enqueue(new Callback<ResponseApi>() {
            @Override
            public void onResponse(Call<ResponseApi> call, Response<ResponseApi> response) {
                ResponseApi responseApi = response.body();
                if(response.isSuccessful() && responseApi != null){
                    Toast.makeText(context, "Clave cambiada correctamente", Toast.LENGTH_SHORT).show();
                }
                else if(response.code() == 401){
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                else{
                    Log.d("SALIDA onResponse", response.message());
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseApi> call, Throwable throwable) {
                Log.d("SALIDA onFailure", throwable.getMessage());
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
