package com.arriola.inmobiliaria.ui.menu.ui.inicio;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arriola.inmobiliaria.R;
import com.arriola.inmobiliaria.databinding.ActivityMainBinding;
import com.arriola.inmobiliaria.databinding.FragmentInicioBinding;
import com.arriola.inmobiliaria.ui.login.MainActivityViewModel;
import com.google.android.gms.maps.SupportMapFragment;

public class InicioFragment extends Fragment {
    private FragmentInicioBinding bind;
    private InicioFragmentViewModel vm;
    public InicioFragment() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bind = FragmentInicioBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(InicioFragmentViewModel.class);


        vm.getmMapaActual().observe(getViewLifecycleOwner(), new Observer<InicioFragmentViewModel.MapaActual>() {
            @Override
            public void onChanged(InicioFragmentViewModel.MapaActual mapaActual) {
                SupportMapFragment mapa = (SupportMapFragment) (getChildFragmentManager().findFragmentById(R.id.mapInmobiliaria));
                mapa.getMapAsync(mapaActual);
            }
        });

        vm.obtenerMapa();

        return bind.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind = null;
    }
}