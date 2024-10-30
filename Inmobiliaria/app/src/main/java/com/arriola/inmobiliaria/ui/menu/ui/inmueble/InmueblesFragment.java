package com.arriola.inmobiliaria.ui.menu.ui.inmueble;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arriola.inmobiliaria.R;
import com.arriola.inmobiliaria.databinding.FragmentInmueblesBinding;
import com.arriola.inmobiliaria.model.inmueble.Inmueble;

import java.util.List;

public class InmueblesFragment extends Fragment {
    private FragmentInmueblesBinding bind;
    private InmueblesFragmentViewModel vm;
    public InmueblesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        bind = FragmentInmueblesBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(InmueblesFragmentViewModel.class);

        bind.fabAgregarInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("idInmueble", 0);
                Navigation.findNavController(container).navigate(R.id.action_inmueblesFragment_to_agregarInmuebleFragment, bundle);
            }
        });

        vm.getMlInmueble().observe(getViewLifecycleOwner(), new Observer<List<Inmueble>>() {
            @Override
            public void onChanged(List<Inmueble> inmuebles) {
                InmuebleAdpter adapter = new InmuebleAdpter(inmuebles);

                bind.rvInmueblesList.setAdapter(adapter);
            }
        });

        vm.GetInmuebles();
        return bind.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bind = null;
    }
}