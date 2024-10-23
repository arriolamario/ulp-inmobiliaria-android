package com.arriola.inmobiliaria.ui.menu.ui.inicio;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arriola.inmobiliaria.R;
import com.arriola.inmobiliaria.databinding.ActivityMainBinding;
import com.arriola.inmobiliaria.databinding.FragmentInicioBinding;
import com.arriola.inmobiliaria.ui.login.MainActivityViewModel;

public class InicioFragment extends Fragment {
    FragmentInicioBinding bind;
    InicioFragmentViewModel vm;
    public InicioFragment() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bind = FragmentInicioBinding.inflate(inflater, container, false);
        View root = bind.getRoot();
        vm = new ViewModelProvider(this).get(InicioFragmentViewModel.class);
        //vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(MainActivityViewModel.class);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind = null;
    }
}