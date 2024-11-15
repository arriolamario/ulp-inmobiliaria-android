package com.arriola.inmobiliaria.ui.menu.ui.cambiarclave;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.Navigation;

import com.arriola.inmobiliaria.R;
import com.arriola.inmobiliaria.model.ResponseApi;
import com.arriola.inmobiliaria.request.ApiClient;
import com.arriola.inmobiliaria.ui.login.MainActivity;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CambiarClaveFragmentViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<Boolean> mCambiarClave;
    public CambiarClaveFragmentViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public MutableLiveData<Boolean> getmCambiarClave() {
        if(mCambiarClave == null){
            mCambiarClave = new MutableLiveData<>();
        }
        return mCambiarClave;
    }

    public void CambiarClave(String clave){
        Gson gson = new Gson();

        Call<ResponseApi> responseApiCall = ApiClient.getApiInmobiliaria(context).cambiarClave(ApiClient.getToken(context).getTokenHeader(), gson.toJson(clave));

        responseApiCall.enqueue(new Callback<ResponseApi>() {
            @Override
            public void onResponse(Call<ResponseApi> call, Response<ResponseApi> response) {
                ResponseApi responseApi = response.body();
                if(response.isSuccessful() && responseApi != null){
                    Toast.makeText(context, "Clave cambiada correctamente", Toast.LENGTH_SHORT).show();
                    mCambiarClave.postValue(true);
                }
                else if(response.code() != 401){
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
