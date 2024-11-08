package com.arriola.inmobiliaria.ui.menu.ui.inquilino;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.arriola.inmobiliaria.model.inquilino.Inquilino;
import com.arriola.inmobiliaria.model.inquilino.InquilinoApi;
import com.arriola.inmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleInquilinoFragmentViewModel extends AndroidViewModel {
    private MutableLiveData<Inquilino> mInquilino;
    private Context context;

    public DetalleInquilinoFragmentViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public MutableLiveData<Inquilino> getmInquilino() {
        if(mInquilino == null)
            mInquilino = new MutableLiveData<>();
        return mInquilino;
    }

    public void getInquilino(int idInquilino)
    {
        Call<InquilinoApi> inquilinoApiCall = ApiClient.getApiInmobiliaria(context)
                .inmueblesAlquilados(ApiClient.getToken(context).getTokenHeader(), Integer.toString(idInquilino));

        inquilinoApiCall.enqueue(new Callback<InquilinoApi>() {
            @Override
            public void onResponse(Call<InquilinoApi> call, Response<InquilinoApi> response) {
                InquilinoApi result = response.body();
                if(response.isSuccessful() && result != null){
                    mInquilino.postValue(result.getData());
                }
            }

            @Override
            public void onFailure(Call<InquilinoApi> call, Throwable throwable) {

            }
        });
    }
}
