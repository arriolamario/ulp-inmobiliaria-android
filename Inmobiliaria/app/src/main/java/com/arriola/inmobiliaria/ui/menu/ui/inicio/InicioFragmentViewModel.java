package com.arriola.inmobiliaria.ui.menu.ui.inicio;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class InicioFragmentViewModel extends AndroidViewModel {
    public static final LatLng SANLUIS = new LatLng(-33.27654765672341, -66.34379088878633);
    private MutableLiveData<MapaActual> mMapaActual;
    public InicioFragmentViewModel(@NonNull Application application) {
        super(application);

    }

    public MutableLiveData<MapaActual> getmMapaActual() {
        if(mMapaActual == null){
            mMapaActual = new MutableLiveData<>();
        }
        return mMapaActual;
    }

    public void obtenerMapa(){
        MapaActual mapaActual = new MapaActual();

        mMapaActual.setValue(mapaActual);

    }

    public class MapaActual implements OnMapReadyCallback{

        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            googleMap.addMarker(new MarkerOptions().position(SANLUIS).title("Inmobiliaria CA"));

            CameraPosition cp = new CameraPosition.Builder()
                    .target(SANLUIS)
                    .zoom(19)
                    //.bearing(45)
                    //.tilt(70)
                    .build();

            CameraUpdate cu = CameraUpdateFactory.newCameraPosition(cp);

            googleMap.animateCamera(cu);
        }
    }
}
