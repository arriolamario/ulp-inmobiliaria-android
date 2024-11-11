package com.arriola.inmobiliaria.ui.menu.ui.perfil;

import static android.app.Activity.RESULT_OK;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.arriola.inmobiliaria.Util;
import com.arriola.inmobiliaria.model.Propietario;
import com.arriola.inmobiliaria.model.ResponseApi;
import com.arriola.inmobiliaria.request.ApiClient;
import com.arriola.inmobiliaria.ui.login.MainActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilFragmentViewModel extends AndroidViewModel {
    private MutableLiveData<Propietario> mPropietario;
    private boolean editable;
    private Propietario propietario;
    private MutableLiveData<Boolean> mEditable;
    private Context context;
    private Uri fotoPerfilUri;
    private MutableLiveData<Bitmap> bitmapMutableLiveData;

    public PerfilFragmentViewModel(@NonNull Application application) {
        super(application);
        editable = false;
        context = application.getApplicationContext();
    }

    public MutableLiveData<Propietario> getmPropietario(){
        if(mPropietario==null)
            mPropietario = new MutableLiveData<>();
        return mPropietario;
    }

    public MutableLiveData<Boolean> getmEditable(){
        if(mEditable==null)
            mEditable = new MutableLiveData<Boolean>();
        return mEditable;
    }

    public LiveData<Bitmap> getBitmapMutable(){

        if(bitmapMutableLiveData==null){
            bitmapMutableLiveData=new MutableLiveData<>();
        }
        return bitmapMutableLiveData;
    }

    public void datosPropietarios(){
        Call<Propietario> propietarioCall = ApiClient.getApiInmobiliaria(context).get(ApiClient.getToken(context).getTokenHeader());

        propietarioCall.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                propietario = response.body();
                if(response.isSuccessful() && propietario != null){
                    mPropietario.setValue(propietario);
                }
                else if(response.code() != 401){
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable throwable) {
                Toast.makeText(context, "onFailure: Error de respuesta API, GetPropietario ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void Editar(String documento, String nombre, String apellido, String email, String area, String numero, Button button){
        if(editable){
            //Grabamos los datos
            propietario.setApellido(apellido);
            propietario.setDocumento(documento);
            propietario.setNombre(nombre);
            propietario.setEmail(email);
            propietario.setTelefonoArea(area);
            propietario.setTelefonoNumero(numero);


            Call<ResponseApi> responseApiCall = ApiClient.getApiInmobiliaria(context).actualizar(ApiClient.getToken(context).getTokenHeader(), propietario);

            responseApiCall.enqueue(new Callback<ResponseApi>() {
                @Override
                public void onResponse(Call<ResponseApi> call, Response<ResponseApi> response) {
                    ResponseApi responseApi = response.body();
                    if(response.isSuccessful() && responseApi != null && responseApi.getStatus().equals("exito")){
                        Toast.makeText(context, "Propietario actualizado", Toast.LENGTH_SHORT).show();
                    }
                    else if(response.code() != 401){
                    }
                }

                @Override
                public void onFailure(Call<ResponseApi> call, Throwable throwable) {
                    Toast.makeText(context, "onFailure, Ocurrio un error!", Toast.LENGTH_SHORT).show();
                }
            });

            button.setText("EDITAR");
        }
        else{
            button.setText("GRABAR");
        }

        editable = !editable;
        mEditable.setValue(editable);
    }

    public void recibirFoto(ActivityResult result) {
        if(result.getResultCode() == RESULT_OK){

            fotoPerfilUri =result.getData().getData();
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
                context.getContentResolver().takePersistableUriPermission (fotoPerfilUri, Intent.FLAG_GRANT_READ_URI_PERMISSION|Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }

            Bitmap imagenBitmap = Util.redimensionarImagenDesdeUri(context, fotoPerfilUri, 800);

            bitmapMutableLiveData.setValue(imagenBitmap);

            String tipoMime = context.getContentResolver().getType(fotoPerfilUri);
            File fileImagen = Util.convertirBitmapAFile(imagenBitmap, context, tipoMime);

            // Crear RequestBody para el archivo
            RequestBody requestFile = RequestBody.create(MediaType.parse(tipoMime), fileImagen);

            // Crear MultipartBody.Part para enviarlo en el form-data
            MultipartBody.Part body = MultipartBody.Part.createFormData("avatar", fileImagen.getName(), requestFile);
            Call<ResponseApi> callResponse = ApiClient.getApiInmobiliaria(context).cambiarAvatar(ApiClient.getToken(context).getTokenHeader(), body);
            
            callResponse.enqueue(new Callback<ResponseApi>() {
                @Override
                public void onResponse(Call<ResponseApi> call, Response<ResponseApi> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(context, "Se cambio la imagen correctamente", Toast.LENGTH_SHORT).show();
                    }
                    else if(response.code() != 401){
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseApi> call, Throwable throwable) {
                    Toast.makeText(context, "Error onFailure", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }


}
