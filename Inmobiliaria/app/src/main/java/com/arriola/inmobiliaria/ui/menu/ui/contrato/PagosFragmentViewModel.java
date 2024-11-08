package com.arriola.inmobiliaria.ui.menu.ui.contrato;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.arriola.inmobiliaria.model.contrato.ContratoPagosApi;
import com.arriola.inmobiliaria.model.contrato.Pago;
import com.arriola.inmobiliaria.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PagosFragmentViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<List<Pago>> mListPagos;
    public PagosFragmentViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public MutableLiveData<List<Pago>> getmListPagos() {
        if(mListPagos == null){
            mListPagos = new MutableLiveData<>();
        }
        return mListPagos;
    }

    public void getPagos(int idContrato){
        Call<ContratoPagosApi> contratoPagosApiCall = ApiClient.getApiInmobiliaria(context).getContratoPagos(ApiClient.getToken(context).getTokenHeader(), Integer.toString(idContrato));
        contratoPagosApiCall.enqueue(new Callback<ContratoPagosApi>() {
            @Override
            public void onResponse(Call<ContratoPagosApi> call, Response<ContratoPagosApi> response) {
                ContratoPagosApi contratoPagosApi = response.body();
                if(response.isSuccessful() && contratoPagosApi != null){
                    mListPagos.postValue(contratoPagosApi.getData());
                }
            }

            @Override
            public void onFailure(Call<ContratoPagosApi> call, Throwable throwable) {

            }
        });
    }
}
