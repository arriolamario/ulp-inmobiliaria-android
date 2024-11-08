package com.arriola.inmobiliaria.request;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.arriola.inmobiliaria.ui.login.MainActivity;
import com.arriola.inmobiliaria.ui.menu.MenuActivity;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    private Context context;

    public AuthInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);

        // Verifica si el código de respuesta es 401
        if(response.code() == 401) {
            try{
                ApiClient.Borrar(context);
                //Toast.makeText(context, "Sesión Expirada!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("FLAGSESSION", true);
                context.startActivity(intent);
            }
            catch (Exception e){
                Log.d("INTERCEPTOR", "intercept: " + e.getMessage());
            }

        }

        return response;
    }
}
