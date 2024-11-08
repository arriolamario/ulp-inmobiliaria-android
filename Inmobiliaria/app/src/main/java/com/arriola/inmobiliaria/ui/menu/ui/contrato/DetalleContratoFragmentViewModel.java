package com.arriola.inmobiliaria.ui.menu.ui.contrato;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.arriola.inmobiliaria.model.contrato.Contrato;
import com.arriola.inmobiliaria.model.contrato.ContratoApi;
import com.arriola.inmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleContratoFragmentViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<Contrato> mContrato;
    public DetalleContratoFragmentViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public void getContrato(int idContrato) {
        Call<ContratoApi> contratoApiCall = ApiClient.getApiInmobiliaria(context).getContrato(ApiClient.getToken(context).getTokenHeader(), Integer.toString(idContrato));

        contratoApiCall.enqueue(new Callback<ContratoApi>() {
            @Override
            public void onResponse(Call<ContratoApi> call, Response<ContratoApi> response) {
                ContratoApi contratoApi = response.body();
                if(response.isSuccessful() && contratoApi != null){
                    mContrato.postValue(contratoApi.getData());
                }
            }

            @Override
            public void onFailure(Call<ContratoApi> call, Throwable throwable) {
                Toast.makeText(context, "Error onFailure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public MutableLiveData<Contrato> getmContrato() {
        if(mContrato == null)
            mContrato = new MutableLiveData<>();
        return mContrato;
    }
}
