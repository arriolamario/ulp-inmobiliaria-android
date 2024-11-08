package com.arriola.inmobiliaria.ui.menu.ui.inmueble;

import static android.content.Intent.getIntent;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.arriola.inmobiliaria.R;
import com.arriola.inmobiliaria.Util;
import com.arriola.inmobiliaria.databinding.FragmentAgregarInmuebleBinding;
import com.arriola.inmobiliaria.databinding.FragmentInmueblesBinding;
import com.arriola.inmobiliaria.model.inmueble.Inmueble;
import com.arriola.inmobiliaria.model.inmueble.Tipo;
import com.arriola.inmobiliaria.model.inmueble.TiposApi;
import com.arriola.inmobiliaria.request.ApiClient;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class AgregarInmuebleFragment extends Fragment {

    private FragmentAgregarInmuebleBinding bind;
    private AgregarInmuebleFragmentViewModel vm;
    private int idInmueble;
    private TiposApi.TiposData tipos;
    private Intent intent;
    private ActivityResultLauncher<Intent> arl;
    private ArrayAdapter<Tipo> adapterTipo;
    private ArrayAdapter<Tipo> adapterUso;
    public AgregarInmuebleFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        bind = FragmentAgregarInmuebleBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(AgregarInmuebleFragmentViewModel.class);

        abrirGaleria();
        idInmueble =  getArguments().getInt("idInmueble");

        vm.getmAgregarInmueble().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                bind.btnAccionAgregarInmueble.setText("CARGAR");
                bind.swActivoAgregarInmueble.setVisibility(View.INVISIBLE);
                bind.swActivoAgregarInmueble.setChecked(true);
                bind.ivfotoAgregarInmueble.setImageResource(R.drawable.agente_inmobiliario);
            }
        });
        vm.getmTipos().observe(getViewLifecycleOwner(), new Observer<TiposApi.TiposData>() {
            @Override
            public void onChanged(TiposApi.TiposData tiposData) {
                tipos = tiposData;

                adapterTipo = new ArrayAdapter<>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, tipos.getTipo());
                bind.spTipoAgregarInmueble.setAdapter(adapterTipo);

                adapterUso = new ArrayAdapter<>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, tipos.getUso());
                bind.spUsoAgregarInmueble.setAdapter(adapterUso);
                vm.getInmueble();
            }
        });
        vm.getmBitmap().observe(getViewLifecycleOwner(), new Observer<Bitmap>() {
            @Override
            public void onChanged(Bitmap bitmap) {
                try{
                    bind.ivfotoAgregarInmueble.setImageBitmap(bitmap);
                }
                catch (Exception ex){
                    Toast.makeText(getContext(), "Error al cargar", Toast.LENGTH_SHORT).show();
                }

            }
        });
        vm.getmCrearInmueble().observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                Navigation.findNavController(container).navigate(R.id.action_agregarInmuebleFragment_to_inmueblesFragment);
            }
        });

        vm.getmActualizarInmueble().observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                bind.btnAccionAgregarInmueble.setText("ACTUALIZAR");
                bind.swActivoAgregarInmueble.setVisibility(View.VISIBLE);

                bind.etDireccionAgregarInmueble.setFocusable(false);
                bind.etDireccionAgregarInmueble.setEnabled(false);
                bind.etDireccionAgregarInmueble.setFocusableInTouchMode(false);
                bind.etDireccionAgregarInmueble.setInputType(InputType.TYPE_CLASS_TEXT);

                bind.etAmbientesAgregarInmueble.setFocusable(false);
                bind.etAmbientesAgregarInmueble.setEnabled(false);
                bind.etAmbientesAgregarInmueble.setFocusableInTouchMode(false);
                bind.etAmbientesAgregarInmueble.setInputType(InputType.TYPE_CLASS_NUMBER);

                bind.etPrecioAgregarInmueble.setFocusable(false);
                bind.etPrecioAgregarInmueble.setEnabled(false);
                bind.etPrecioAgregarInmueble.setFocusableInTouchMode(false);
                bind.etPrecioAgregarInmueble.setInputType(InputType.TYPE_CLASS_NUMBER);

                bind.spTipoAgregarInmueble.setFocusable(false);
                bind.spTipoAgregarInmueble.setEnabled(false);
                bind.spTipoAgregarInmueble.setFocusableInTouchMode(false);

                bind.spUsoAgregarInmueble.setFocusable(false);
                bind.spUsoAgregarInmueble.setEnabled(false);
                bind.spUsoAgregarInmueble.setFocusableInTouchMode(false);

            }
        });
        vm.getmGetInmueble().observe(getViewLifecycleOwner(), new Observer<Inmueble>() {
            @Override
            public void onChanged(Inmueble inmueble) {
                Util.cargarImagen(getContext(), inmueble.getAvatar_Url(), R.drawable.agente_inmobiliario, bind.ivfotoAgregarInmueble);

                bind.etDireccionAgregarInmueble.setText(inmueble.getDireccion());
                bind.etAmbientesAgregarInmueble.setText(Integer.toString(inmueble.getAmbientes()));
                bind.etPrecioAgregarInmueble.setText(Float.toString(inmueble.getPrecio()));
                Tipo t = inmueble.getTipo();
                int positionTipo = adapterTipo.getPosition(t);
                bind.spTipoAgregarInmueble.setSelection(positionTipo);
                int positionUso = adapterUso.getPosition(inmueble.getUso());
                bind.spUsoAgregarInmueble.setSelection(positionUso);
                bind.swActivoAgregarInmueble.setChecked(inmueble.isActivo());
            }
        });
        bind.ivfotoAgregarInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arl.launch(intent);
            }
        });

        bind.btnAccionAgregarInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String direccion = bind.etDireccionAgregarInmueble.getText().toString();
                String ambientes = bind.etAmbientesAgregarInmueble.getText().toString();
                String precio = bind.etPrecioAgregarInmueble.getText().toString();
                Tipo tipo = (Tipo) bind.spTipoAgregarInmueble.getSelectedItem();
                Tipo uso = (Tipo) bind.spUsoAgregarInmueble.getSelectedItem();
                Bitmap imagenBitmap = ((BitmapDrawable) bind.ivfotoAgregarInmueble.getDrawable()).getBitmap();
                boolean activo = bind.swActivoAgregarInmueble.isChecked();

                vm.grabar(idInmueble, direccion, ambientes, precio, tipo, uso, imagenBitmap, activo);
            }
        });
        vm.setIdInmueble(idInmueble);
        vm.getTipos();



        return bind.getRoot();
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