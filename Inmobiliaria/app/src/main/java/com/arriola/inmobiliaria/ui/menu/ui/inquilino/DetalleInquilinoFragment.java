package com.arriola.inmobiliaria.ui.menu.ui.inquilino;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arriola.inmobiliaria.R;
import com.arriola.inmobiliaria.databinding.FragmentDetalleInquilinoBinding;
import com.arriola.inmobiliaria.databinding.FragmentInmueblesBinding;
import com.arriola.inmobiliaria.model.inquilino.Inquilino;
import com.arriola.inmobiliaria.ui.menu.ui.inmueble.InmueblesFragmentViewModel;

public class DetalleInquilinoFragment extends Fragment {
    public static final String IDINQUILINO = "IDINQUILINO";
    private FragmentDetalleInquilinoBinding bind;
    private DetalleInquilinoFragmentViewModel vm;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bind = FragmentDetalleInquilinoBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(DetalleInquilinoFragmentViewModel.class);

        int idInquilino = getArguments().getInt(IDINQUILINO);


        vm.getmInquilino().observe(getViewLifecycleOwner(), new Observer<Inquilino>() {
            @Override
            public void onChanged(Inquilino inquilino) {
                bind.etApellidoInquilino.setText(inquilino.getApellido());
                bind.etDireccionInquilino.setText(inquilino.getDireccion());
                bind.etDniInquilino.setText(inquilino.getDni());
                bind.etEmailInquilino.setText(inquilino.getEmail());
                bind.etTelefonoInquilino.setText(inquilino.getTelefonoArea() + " - " + inquilino.getTelefonoNumero());
                bind.etNombreInquilino.setText(inquilino.getNombre());
            }
        });

        vm.getInquilino(idInquilino);
        return bind.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bind = null;
    }
}