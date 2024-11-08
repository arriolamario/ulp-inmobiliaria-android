package com.arriola.inmobiliaria.ui.menu.ui.perfil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arriola.inmobiliaria.R;
import com.arriola.inmobiliaria.Util;
import com.arriola.inmobiliaria.databinding.FragmentInicioBinding;
import com.arriola.inmobiliaria.databinding.FragmentPerfilBinding;
import com.arriola.inmobiliaria.model.Propietario;
import com.arriola.inmobiliaria.request.ApiClient;
import com.arriola.inmobiliaria.ui.menu.ui.inicio.InicioFragmentViewModel;
import com.bumptech.glide.Glide;

public class PerfilFragment extends Fragment {
    private FragmentPerfilBinding bind;
    private PerfilFragmentViewModel vm;
    private Intent intent;
    private ActivityResultLauncher<Intent> arl;
    public PerfilFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bind = FragmentPerfilBinding.inflate(inflater, container, false);
        View root = bind.getRoot();
        vm = new ViewModelProvider(this).get(PerfilFragmentViewModel.class);

        abrirGaleria();
        vm.getmPropietario().observe(getViewLifecycleOwner(), new Observer<Propietario>() {
            @Override
            public void onChanged(Propietario propietario) {
                bind.etApellido.setText(propietario.getApellido());
                bind.etNombre.setText(propietario.getNombre());
                bind.etEmail.setText(propietario.getEmail());
                bind.etDocumento.setText(propietario.getDocumento());
                bind.etTelefonoArea.setText(propietario.getTelefonoArea());
                bind.etTelefonoNumero.setText(propietario.getTelefonoNumero());
                bind.ivFotoPerfil.setImageResource(R.drawable.sin_perfil);

                Util.cargarImagen(getContext(), propietario.getAvatar_Url(), R.drawable.sin_perfil, bind.ivFotoPerfil);
            }
        });

        vm.getmEditable().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean){
                bind.etDocumento.setFocusable(aBoolean);
                bind.etDocumento.setEnabled(aBoolean);
                bind.etDocumento.setFocusableInTouchMode(aBoolean);
                bind.etDocumento.setInputType(InputType.TYPE_CLASS_NUMBER);

                bind.etNombre.setFocusable(aBoolean);
                bind.etNombre.setEnabled(aBoolean);
                bind.etNombre.setFocusableInTouchMode(aBoolean);
                bind.etNombre.setInputType(InputType.TYPE_CLASS_TEXT);


                bind.etApellido.setFocusable(aBoolean);
                bind.etApellido.setEnabled(aBoolean);
                bind.etApellido.setFocusableInTouchMode(aBoolean);
                bind.etApellido.setInputType(InputType.TYPE_CLASS_TEXT);

                bind.etEmail.setFocusable(aBoolean);
                bind.etEmail.setEnabled(aBoolean);
                bind.etEmail.setFocusableInTouchMode(aBoolean);
                bind.etEmail.setInputType(InputType.TYPE_CLASS_TEXT);

                bind.etTelefonoArea.setFocusable(aBoolean);
                bind.etTelefonoArea.setEnabled(aBoolean);
                bind.etTelefonoArea.setFocusableInTouchMode(aBoolean);
                bind.etTelefonoArea.setInputType(InputType.TYPE_CLASS_NUMBER);

                bind.etTelefonoNumero.setFocusable(aBoolean);
                bind.etTelefonoNumero.setEnabled(aBoolean);
                bind.etTelefonoNumero.setFocusableInTouchMode(aBoolean);
                bind.etTelefonoNumero.setInputType(InputType.TYPE_CLASS_NUMBER);
            }
        });

        vm.getBitmapMutable().observe(getViewLifecycleOwner(), new Observer<Bitmap>() {
            @Override
            public void onChanged(Bitmap imagen) {
                bind.ivFotoPerfil.setImageBitmap(imagen);
            }
        });

        bind.btEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String documento = bind.etDocumento.getText().toString();
                String nombre = bind.etNombre.getText().toString();
                String apellido = bind.etApellido.getText().toString();
                String email = bind.etEmail.getText().toString();
                String area = bind.etTelefonoArea.getText().toString();
                String numero = bind.etTelefonoNumero.getText().toString();
                vm.Editar(documento, nombre, apellido, email, area, numero, bind.btEditar);
            }
        });

        bind.btCambiarClave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_perfilFragment_to_cambiarClaveFragment);
            }
        });

        bind.ivFotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arl.launch(intent);
            }
        });

        vm.datosPropietarios();

        return root;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bind = null;
    }

    private void abrirGaleria(){
        intent=new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        arl=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                vm.recibirFoto(result);
            }
        });
    }
}