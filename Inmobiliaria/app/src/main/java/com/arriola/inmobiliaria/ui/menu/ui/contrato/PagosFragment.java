package com.arriola.inmobiliaria.ui.menu.ui.contrato;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arriola.inmobiliaria.R;
import com.arriola.inmobiliaria.databinding.FragmentPagosBinding;
import com.arriola.inmobiliaria.model.contrato.Pago;
import com.arriola.inmobiliaria.ui.menu.adapter.PagoAdapter;

import java.util.List;

public class PagosFragment extends Fragment {
    public static final String IDCONTRATO = "IDCONTRATO";
    private FragmentPagosBinding bind;
    private PagosFragmentViewModel vm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bind = FragmentPagosBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(PagosFragmentViewModel.class);

        int idContrato = getArguments().getInt(IDCONTRATO);

        vm.getmListPagos().observe(getViewLifecycleOwner(), new Observer<List<Pago>>() {
            @Override
            public void onChanged(List<Pago> pagos) {
                PagoAdapter adapter = new PagoAdapter(pagos);
                bind.rvListaPagos.setAdapter(adapter);
            }
        });
        vm.getPagos(idContrato);

        return bind.getRoot();
    }
}