package com.arriola.inmobiliaria.ui.menu.ui.inmueble;

import static android.app.Activity.RESULT_OK;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.Navigation;

import com.arriola.inmobiliaria.R;
import com.arriola.inmobiliaria.Util;
import com.arriola.inmobiliaria.model.ResponseApi;
import com.arriola.inmobiliaria.model.inmueble.Inmueble;
import com.arriola.inmobiliaria.model.inmueble.InmuebleApi;
import com.arriola.inmobiliaria.model.inmueble.InmuebleIdApi;
import com.arriola.inmobiliaria.model.inmueble.Tipo;
import com.arriola.inmobiliaria.model.inmueble.TiposApi;
import com.arriola.inmobiliaria.request.ApiClient;
import com.arriola.inmobiliaria.ui.login.MainActivity;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Part;

public class AgregarInmuebleFragmentViewModel extends AndroidViewModel {
    private Context context;
    private int idInmueble;
    private MutableLiveData<TiposApi.TiposData> mTipos;
    private MutableLiveData<String> mAgregarInmueble;
    private MutableLiveData<Bitmap> mBitmap;
    private Uri imagenInmuebleUri;
    private MutableLiveData mCrearInmueble;

    private MutableLiveData mActualizarInmueble;
    private MutableLiveData<Inmueble> mGetInmueble;

    public AgregarInmuebleFragmentViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public MutableLiveData<String> getmAgregarInmueble() {
        if(mAgregarInmueble == null)
            mAgregarInmueble = new MutableLiveData<>();
        return mAgregarInmueble;
    }

    public MutableLiveData<TiposApi.TiposData> getmTipos() {
        if(mTipos == null)
            mTipos = new MutableLiveData<>();
        return mTipos;
    }

    public MutableLiveData<Bitmap> getmBitmap() {
        if(mBitmap == null)
            mBitmap = new MutableLiveData<>();
        return mBitmap;
    }

    public MutableLiveData getmCrearInmueble() {
        if(mCrearInmueble == null){
            mCrearInmueble = new MutableLiveData();
        }
        return mCrearInmueble;
    }

    public MutableLiveData getmActualizarInmueble() {
        if(mActualizarInmueble == null)
            mActualizarInmueble = new MutableLiveData<>();
        return mActualizarInmueble;
    }

    public MutableLiveData<Inmueble> getmGetInmueble() {
        if(mGetInmueble == null)
            mGetInmueble = new MutableLiveData();
        return mGetInmueble;
    }

    public void setIdInmueble(int idInmueble) {
        this.idInmueble = idInmueble;

        if(idInmueble == 0){
            mAgregarInmueble.setValue("CARGAR");
        }
        else{
            mActualizarInmueble.setValue("");
        }
    }

    public void getTipos(){
        Call<TiposApi> callTipos = ApiClient.getApiInmobiliaria().getTipos(ApiClient.getToken(context).getTokenHeader());

        callTipos.enqueue(new Callback<TiposApi>() {
            @Override
            public void onResponse(Call<TiposApi> call, Response<TiposApi> response) {
                TiposApi tipos = response.body();
                if(response.isSuccessful() && tipos != null){
                    mTipos.postValue(tipos.getData());
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
            public void onFailure(Call<TiposApi> call, Throwable throwable) {
                Toast.makeText(context, "Error onFailure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void recibirFoto(ActivityResult result) {
        if(result.getResultCode() == RESULT_OK) {
            imagenInmuebleUri = result.getData().getData();
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
                context.getContentResolver().takePersistableUriPermission(imagenInmuebleUri, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }

            Bitmap imagenBitmap = Util.redimensionarImagenDesdeUri(context, imagenInmuebleUri, 800);
            mBitmap.setValue(imagenBitmap);
        }
    }

    public void grabar(int idInmueble, String direccion, String ambientes, String precio, Tipo tipo, Tipo uso, Bitmap imagenBitmap, boolean activo) {

        if(!direccion.isEmpty() && !ambientes.isEmpty() && !precio.isEmpty() && tipo != null && uso != null && imagenBitmap != null){
            String tipoMime =imagenInmuebleUri != null ? context.getContentResolver().getType(imagenInmuebleUri) : "";
            File fileImagen = Util.convertirBitmapAFile(imagenBitmap, context, tipoMime);

            RequestBody direccionPart = RequestBody.create(MediaType.parse("text/plain"), direccion);
            RequestBody ambientesPart = RequestBody.create(MediaType.parse("text/plain"), ambientes);
            RequestBody precioPart = RequestBody.create(MediaType.parse("text/plain"), precio);
            RequestBody idTipoPart = RequestBody.create(MediaType.parse("text/plain"), Integer.toString(tipo.getId()));
            RequestBody idUsoPart = RequestBody.create(MediaType.parse("text/plain"), Integer.toString(uso.getId()));
            RequestBody activoPart = RequestBody.create(MediaType.parse("text/plain"), Boolean.toString(activo));
            // Crear RequestBody para el archivo
            RequestBody requestFile = RequestBody.create(MediaType.parse(tipoMime), fileImagen);

            // Crear MultipartBody.Part para enviarlo en el form-data
            MultipartBody.Part bodyImagen = MultipartBody.Part.createFormData("imagen", fileImagen.getName(), requestFile);

            if(idInmueble == 0) {
                Call<ResponseApi> responseApiCall = ApiClient.getApiInmobiliaria()
                        .crearInmueble(ApiClient.getToken(context).getTokenHeader(), bodyImagen, direccionPart, ambientesPart, precioPart, idTipoPart, idUsoPart, activoPart);

                responseApiCall.enqueue(new Callback<ResponseApi>() {
                    @Override
                    public void onResponse(Call<ResponseApi> call, Response<ResponseApi> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(context, "Se creo correctamente el inmueble", Toast.LENGTH_SHORT).show();
                            mCrearInmueble.postValue("");
                        } else if (response.code() == 401) {
                            Intent intent = new Intent(context, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        } else {
                            Log.d("SALIDA onResponse", response.message());
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseApi> call, Throwable throwable) {
                        Toast.makeText(context, "Error onFailure", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else{
                Call<ResponseApi> responseApiCall = ApiClient.getApiInmobiliaria().actualizarInmueble(ApiClient.getToken(context).getTokenHeader(), bodyImagen, activoPart, Integer.toString(idInmueble));

                responseApiCall.enqueue(new Callback<ResponseApi>() {
                    @Override
                    public void onResponse(Call<ResponseApi> call, Response<ResponseApi> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(context, "Se actualizo correctamente el inmueble", Toast.LENGTH_SHORT).show();
                            mCrearInmueble.postValue("");
                        } else if (response.code() == 401) {
                            Intent intent = new Intent(context, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        } else {
                            Log.d("SALIDA onResponse", response.message());
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
        else{
            Toast.makeText(context, "Faltan campos requeridos", Toast.LENGTH_SHORT).show();
        }
    }


    public void getInmueble(){
        if(idInmueble != 0){
            Call<InmuebleIdApi> inmuebleIdApiCall = ApiClient.getApiInmobiliaria().getInmuebleId(ApiClient.getToken(context).getTokenHeader(), Integer.toString(idInmueble));

            inmuebleIdApiCall.enqueue(new Callback<InmuebleIdApi>() {
                @Override
                public void onResponse(Call<InmuebleIdApi> call, Response<InmuebleIdApi> response) {
                    InmuebleIdApi responseApi = response.body();
                    if(response.isSuccessful() && responseApi != null){
                        mGetInmueble.postValue(responseApi.getData());
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
                public void onFailure(Call<InmuebleIdApi> call, Throwable throwable) {
                    Toast.makeText(context, "Error onFailure", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
