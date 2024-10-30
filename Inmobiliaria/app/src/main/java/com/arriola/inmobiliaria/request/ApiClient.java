package com.arriola.inmobiliaria.request;

import android.content.Context;
import android.content.SharedPreferences;

import com.arriola.inmobiliaria.model.LoginView;
import com.arriola.inmobiliaria.model.Propietario;
import com.arriola.inmobiliaria.model.ResponseApi;
import com.arriola.inmobiliaria.model.Token;
import com.arriola.inmobiliaria.model.UsuarioToken;
import com.arriola.inmobiliaria.model.inmueble.InmuebleApi;
import com.arriola.inmobiliaria.model.inmueble.InmuebleIdApi;
import com.arriola.inmobiliaria.model.inmueble.TiposApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public class ApiClient {
    public static final String URLBASE = "http://192.168.1.135:5182/";

    private static SharedPreferences sp;
    private static Token token;

    public static InmobiliariaService getApiInmobiliaria(){
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URLBASE)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(InmobiliariaService.class);
    }

    private static SharedPreferences getSharedPreference(Context context){
        if(sp == null){
            sp = context.getSharedPreferences("usuario", 0);
        }
        return sp;
    }

    public static void Guardar(Context context, Token token){
        SharedPreferences sp = getSharedPreference(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("token", token.getToken());
        editor.putLong("expiracion", token.getExpiracion().getTime());
        editor.putString("apellido", token.getUsuario().getApellido());
        editor.putString("nombre", token.getUsuario().getNombre());
        editor.putString("email", token.getUsuario().getEmail());
        editor.putString("urlAvatar", token.getUsuario().getAvatar_Url());
        editor.apply();
    }

    public static Token Leer(Context context){
        SharedPreferences sp = getSharedPreference(context);

        String tokenStr = sp.getString("token", null);
        long expiracion = sp.getLong("expiracion", 0);
        String apellidoStr = sp.getString("apellido", null);
        String nombreStr = sp.getString("nombre", null);
        String emailStr = sp.getString("email", null);
        String urlAvatar = sp.getString("urlAvatar", null);
        UsuarioToken usuarioToken = new UsuarioToken(emailStr, nombreStr, apellidoStr, urlAvatar);
        token = new Token(tokenStr, new Date(expiracion), usuarioToken);
        return token;
    }

    public static Token getToken(Context context) {
        if(token==null){
            token = Leer(context);
        }
        return token;
    }


    public interface InmobiliariaService {

        @POST("api/propietarios/login")
        Call<Token> login(@Body LoginView loginView);

        @GET("api/propietarios")
        Call<Propietario> get(@Header("Authorization") String token);

        @PATCH("api/propietarios")
        Call<ResponseApi> actualizar(@Header("Authorization") String token, @Body Propietario propietario);

        @Headers("Content-Type: application/json")
        @PATCH("api/propietarios/cambiarclave")
        Call<ResponseApi> cambiarClave(@Header("Authorization") String token, @Body String nuevaClave);

        @FormUrlEncoded
        @POST("api/propietarios/usuario")
        Call<ResponseApi> mailRecupero(@Field("usuario")String usuario);

        @Multipart
        @PATCH("api/propietarios/cambiaravatar")
        Call<ResponseApi> cambiarAvatar(@Header("Authorization") String token, @Part MultipartBody.Part avatar);

        @GET("api/inmueble")
        Call<InmuebleApi> inmuebles(@Header("Authorization") String token);

        @GET("api/tipos")
        Call<TiposApi> getTipos(@Header("Authorization") String token);

        @Multipart
        @PUT("api/inmueble")
        Call<ResponseApi> crearInmueble(@Header("Authorization") String token,
                                        @Part MultipartBody.Part imagen,
                                        @Part("Direccion") RequestBody direccion,
                                        @Part("Ambientes") RequestBody ambientes,
                                        @Part("Precio") RequestBody precio,
                                        @Part("IdTipo") RequestBody idTipo,
                                        @Part("IdUso") RequestBody idUso,
                                        @Part("Activo") RequestBody activo);

        @GET("api/inmueble/{idInmueble}")
        Call<InmuebleIdApi> getInmuebleId(@Header("Authorization") String token, @Path("idInmueble") String idInmueble);

        @Multipart
        @PATCH("api/inmueble/{idInmueble}")
        Call<ResponseApi> actualizarInmueble(@Header("Authorization") String token, @Part MultipartBody.Part imagen, @Part("Activo") RequestBody activo, @Path("idInmueble") String idInmueble);
    }
}
