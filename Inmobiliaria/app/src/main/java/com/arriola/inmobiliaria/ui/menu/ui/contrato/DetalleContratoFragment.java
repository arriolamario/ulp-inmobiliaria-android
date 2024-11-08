package com.arriola.inmobiliaria.ui.menu.ui.contrato;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arriola.inmobiliaria.R;
import com.arriola.inmobiliaria.databinding.FragmentDetalleContratoBinding;
import com.arriola.inmobiliaria.model.contrato.Contrato;

import java.text.SimpleDateFormat;

public class DetalleContratoFragment extends Fragment {
    public static final String IDCONTRATO = "idContrato";
    private FragmentDetalleContratoBinding bind;
    private DetalleContratoFragmentViewModel vm;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bind = FragmentDetalleContratoBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(DetalleContratoFragmentViewModel.class);

        int idContrato = getArguments().getInt(IDCONTRATO);


        vm.getmContrato().observe(getViewLifecycleOwner(), new Observer<Contrato>() {
            @Override
            public void onChanged(Contrato contrato) {
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                bind.etFechaInicioContrato.setText(formato.format(contrato.getFechaDesde()));
                bind.etFechaFinalizacionContrato.setText(formato.format(contrato.getFechaHasta()));
                bind.etMontoAlquilerContrato.setText(contrato.getMontoAlquiler()+"");
                bind.etInquilinoContrato.setText(contrato.getInquilino().getApellido() + " " + contrato.getInquilino().getNombre());
                bind.etDireccionInmuebleContrato.setText(contrato.getInmueble().getDireccion());

                bind.btPagosContrato.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putInt(PagosFragment.IDCONTRATO, contrato.getId());
                        Navigation.findNavController(bind.getRoot()).navigate(R.id.action_detalleContratoFragment_to_pagosFragment, bundle);
                    }
                });
            }
        });

        vm.getContrato(idContrato);
        return bind.getRoot();
    }

    @Override
    public void onDestroy() {
        bind = null;
        super.onDestroy();

    }
}