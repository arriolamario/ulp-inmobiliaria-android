package com.arriola.inmobiliaria.ui.menu.ui.inquilino;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arriola.inmobiliaria.databinding.FragmentInquilinosBinding;
import com.arriola.inmobiliaria.model.inmueble.Inmueble;
import com.arriola.inmobiliaria.ui.menu.adapter.InmuebleAdpter;

import java.util.List;


public class InquilinosFragment extends Fragment {
    private FragmentInquilinosBinding bind;
    private InquilinosFragmentViewModel vm;
     @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         bind = FragmentInquilinosBinding.inflate(inflater, container, false);
         vm = new ViewModelProvider(this).get(InquilinosFragmentViewModel.class);


         vm.getMlInmueble().observe(getViewLifecycleOwner(), new Observer<List<Inmueble>>() {
             @Override
             public void onChanged(List<Inmueble> inmuebles) {
                 InmuebleAdpter adapter = new InmuebleAdpter(inmuebles, InmuebleAdpter.INQUILINO_DETALLE);

                 bind.rvInmuebleAlquilados.setAdapter(adapter);
             }
         });

         vm.getInmuebles();

        return bind.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bind = null;
    }
}