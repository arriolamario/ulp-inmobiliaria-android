package com.arriola.inmobiliaria.ui.menu.ui.cambiarclave;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arriola.inmobiliaria.R;
import com.arriola.inmobiliaria.databinding.FragmentCambiarClaveBinding;
import com.arriola.inmobiliaria.databinding.FragmentPerfilBinding;
import com.arriola.inmobiliaria.ui.menu.ui.perfil.PerfilFragmentViewModel;

public class CambiarClaveFragment extends Fragment {
    private FragmentCambiarClaveBinding bind;
    private CambiarClaveFragmentViewModel vm;
    public CambiarClaveFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bind = FragmentCambiarClaveBinding.inflate(inflater, container, false);
        View root = bind.getRoot();
        vm = new ViewModelProvider(this).get(CambiarClaveFragmentViewModel.class);

        bind.btCambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vm.CambiarClave(bind.etCambiarClave.getText().toString());
            }
        });

        vm.getmCambiarClave().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Navigation.findNavController(container).navigate(R.id.action_cambiarClaveFragment_to_perfilFragment);
            }
        });
        return root;
    }
}