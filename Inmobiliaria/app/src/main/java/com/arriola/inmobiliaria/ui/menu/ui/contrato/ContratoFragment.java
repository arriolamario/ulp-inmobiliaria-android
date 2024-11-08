package com.arriola.inmobiliaria.ui.menu.ui.contrato;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arriola.inmobiliaria.R;
import com.arriola.inmobiliaria.databinding.FragmentContratoBinding;
import com.arriola.inmobiliaria.model.inmueble.Inmueble;
import com.arriola.inmobiliaria.ui.menu.adapter.InmuebleAdpter;

import java.util.List;

public class ContratoFragment extends Fragment {
    private FragmentContratoBinding bind;
    private ContratoFragmentViewModel vm;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bind = FragmentContratoBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(ContratoFragmentViewModel.class);

        vm.getmListInmueble().observe(getViewLifecycleOwner(), new Observer<List<Inmueble>>() {
            @Override
            public void onChanged(List<Inmueble> inmuebles) {
                InmuebleAdpter adapter = new InmuebleAdpter(inmuebles, InmuebleAdpter.CONTRATO_DETALLE);
                bind.rvInmuebleContrato.setAdapter(adapter);
            }
        });

        vm.getInmueblesAlquilados();

        return bind.getRoot();
    }

    @Override
    public void onDestroy() {
        bind = null;
        super.onDestroy();
    }
}