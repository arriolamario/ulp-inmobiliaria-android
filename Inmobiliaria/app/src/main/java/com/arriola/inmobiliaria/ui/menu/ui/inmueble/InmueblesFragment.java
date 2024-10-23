package com.arriola.inmobiliaria.ui.menu.ui.inmueble;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arriola.inmobiliaria.R;
import com.arriola.inmobiliaria.databinding.FragmentInmueblesBinding;

public class InmueblesFragment extends Fragment {
    private FragmentInmueblesBinding bind;
    private InmueblesFragmentViewModel vm;
    public InmueblesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bind = FragmentInmueblesBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(InmueblesFragmentViewModel.class);
        return bind.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bind = null;
    }
}