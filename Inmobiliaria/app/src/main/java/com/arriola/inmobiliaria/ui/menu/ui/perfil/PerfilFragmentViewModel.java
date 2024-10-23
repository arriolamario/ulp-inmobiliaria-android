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
        Call<Propietario> propietarioCall = ApiClient.getApiInmobiliaria().get(ApiClient.getToken(context).getTokenHeader());

        propietarioCall.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                propietario = response.body();
                if(response.isSuccessful() && propietario != null){
                    mPropietario.setValue(propietario);
                }
                else if(response.code() == 401){
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                else{
                    Log.d("SALIDA", response.message()) ;
                    Toast.makeText(context, "onResponse: Error de respuesta API, GetPropietario", Toast.LENGTH_SHORT).show();
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


            Call<ResponseApi> responseApiCall = ApiClient.getApiInmobiliaria().actualizar(ApiClient.getToken(context).getTokenHeader(), propietario);

            responseApiCall.enqueue(new Callback<ResponseApi>() {
                @Override
                public void onResponse(Call<ResponseApi> call, Response<ResponseApi> response) {
                    ResponseApi responseApi = response.body();
                    if(response.isSuccessful() && responseApi != null && responseApi.getStatus().equals("exito")){
                        Toast.makeText(context, "Propietario actualizado", Toast.LENGTH_SHORT).show();
                    }
                    else if(response.code() == 401){
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                    else{
                        Log.d("SALIDA", "onResponse: " + response.message());
                        Toast.makeText(context, "onResponse, Error en la respuesta, actualizar propietario", Toast.LENGTH_SHORT).show();
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

            Bitmap imagenBitmap = redimensionarImagenDesdeUri(context, fotoPerfilUri, 800);

            bitmapMutableLiveData.setValue(imagenBitmap);

            String tipoMime = context.getContentResolver().getType(fotoPerfilUri);
            File fileImagen = convertirBitmapAFile(imagenBitmap, context, tipoMime);

            // Crear RequestBody para el archivo
            RequestBody requestFile = RequestBody.create(MediaType.parse(tipoMime), fileImagen);

            // Crear MultipartBody.Part para enviarlo en el form-data
            MultipartBody.Part body = MultipartBody.Part.createFormData("avatar", fileImagen.getName(), requestFile);
            Call<ResponseApi> callResponse = ApiClient.getApiInmobiliaria().cambiarAvatar(ApiClient.getToken(context).getTokenHeader(), body);
            
            callResponse.enqueue(new Callback<ResponseApi>() {
                @Override
                public void onResponse(Call<ResponseApi> call, Response<ResponseApi> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(context, "Se cambio la imagen correctamente", Toast.LENGTH_SHORT).show();
                    }
                    else if(response.code() == 401){
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                    else{
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

    private Bitmap redimensionarImagenDesdeUri(Context context, Uri uri, int nuevoAncho) {
        try {
            //Obtenemos el bitmap desde la URI
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            Bitmap bitmapOriginal = BitmapFactory.decodeStream(inputStream);
            
            //Redimencionamos
            int anchoOriginal = bitmapOriginal.getWidth();
            int alturaOriginal = bitmapOriginal.getHeight();

            int nuevaAltura = (int) ((double) alturaOriginal / anchoOriginal * nuevoAncho);

            bitmapOriginal = Bitmap.createScaledBitmap(bitmapOriginal, nuevoAncho, nuevaAltura, true);

            int orientacion = obtenerOrientacionExif(context, uri);
            bitmapOriginal = rotarImagenSegunExif(bitmapOriginal, orientacion);
            
            return bitmapOriginal;
        } catch (FileNotFoundException e) {
            Log.e("FILE-OPEN", e.getMessage());
            return null;
        }
    }

    private int obtenerOrientacionExif(Context context, Uri uri) {
        try {
            InputStream input = context.getContentResolver().openInputStream(uri);
            ExifInterface exif = new ExifInterface(input);

            int orientacion = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            input.close();
            return orientacion;
        } catch (IOException e) {
            return ExifInterface.ORIENTATION_UNDEFINED;
        }
    }

    private Bitmap rotarImagenSegunExif(Bitmap bitmap, int orientacionExif) {
        Matrix matrix = new Matrix();

        switch (orientacionExif) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.postRotate(90);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.postRotate(180);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.postRotate(270);
                break;
            default:
                return bitmap; // No se necesita rotar
        }

        // Crear un nuevo bitmap con la rotación aplicada
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public File convertirBitmapAFile(Bitmap bitmap, Context context, String tipoMime) {
        // Crear un archivo temporal
        String nombreFile = "nombreFile.jpeg";
        if(tipoMime == "image/jpeg")
            nombreFile = "imagen_temp.jpeg";
        else if(tipoMime == "image/png")
            nombreFile = "imagen_temp.png";
        File file = new File(context.getCacheDir(), nombreFile);
        try{
            file.createNewFile();
            // Convertir Bitmap a un byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos); // Puedes cambiar a JPEG si prefieres
            byte[] bitmapData = bos.toByteArray();

            // Escribir el byte array a un archivo
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapData);
            fos.flush();
            fos.close();
        }
        catch (Exception ex){

        }

        return file;
    }
}
