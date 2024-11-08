package com.arriola.inmobiliaria;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import com.arriola.inmobiliaria.request.ApiClient;
import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Util {

    public static void cargarImagen(Context context, String url, int idResource, ImageView ivImagen){
        LocalDateTime fechaActual = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String fechaFormateada = fechaActual.format(formato);
        Glide.with(context)
                .load(ApiClient.URLBASE + url + "?fecha="+ fechaFormateada)
                .placeholder(R.drawable.cargando)
                .error(idResource)
                .into(ivImagen);
    }

    public static Bitmap redimensionarImagenDesdeUri(Context context, Uri uri, int nuevoAncho) {
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

    private static int obtenerOrientacionExif(Context context, Uri uri) {
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

    private static Bitmap rotarImagenSegunExif(Bitmap bitmap, int orientacionExif) {
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

    public static File convertirBitmapAFile(Bitmap bitmap, Context context, String tipoMime) {
        // Crear un archivo temporal
        String nombreFile = "imagen_temp.jpeg";
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
