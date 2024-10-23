package com.arriola.inmobiliaria.ui.menu;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.arriola.inmobiliaria.model.Token;
import com.arriola.inmobiliaria.request.ApiClient;

public class MenuActivityViewModel extends AndroidViewModel {
    private MutableLiveData<Token> mToken;
    private Context context;
    public MenuActivityViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public MutableLiveData<Token> getmToken(){
        if(mToken == null)
            mToken = new MutableLiveData<>();
        return mToken;
    }


    public void datosPropietario(){

        mToken.setValue(ApiClient.getToken(context));
    }


}
