package com.arriola.inmobiliaria.ui.menu.ui.contrato;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.arriola.inmobiliaria.model.inmueble.Inmueble;
import com.arriola.inmobiliaria.model.inmueble.InmuebleApi;
import com.arriola.inmobiliaria.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContratoFragmentViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<List<Inmueble>> mListInmueble;
    public ContratoFragmentViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public MutableLiveData<List<Inmueble>> getmListInmueble() {
        if(mListInmueble == null)
            mListInmueble = new MutableLiveData<>();
        return mListInmueble;
    }

    public void getInmueblesAlquilados(){
        Call<InmuebleApi> inmuebleApiCall = ApiClient.getApiInmobiliaria(context).inmueblesAlquilados(ApiClient.getToken(context).getTokenHeader());

        inmuebleApiCall.enqueue(new Callback<InmuebleApi>() {
            @Override
            public void onResponse(Call<InmuebleApi> call, Response<InmuebleApi> response) {
                InmuebleApi inmuebleApi = response.body();
                if(response.isSuccessful() && inmuebleApi != null){
                    mListInmueble.postValue(inmuebleApi.getData());
                }
                else if(response.code() != 401){
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<InmuebleApi> call, Throwable throwable) {
                Toast.makeText(context, "Fallo onFailure", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
