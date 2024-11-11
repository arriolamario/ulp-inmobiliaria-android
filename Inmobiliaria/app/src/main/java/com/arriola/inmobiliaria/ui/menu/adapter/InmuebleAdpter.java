package com.arriola.inmobiliaria.ui.menu.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.arriola.inmobiliaria.ui.menu.ui.contrato.DetalleContratoFragment;
import com.arriola.inmobiliaria.R;
import com.arriola.inmobiliaria.Util;
import com.arriola.inmobiliaria.databinding.ViewInmuebleItemBinding;
import com.arriola.inmobiliaria.model.inmueble.Inmueble;
import com.arriola.inmobiliaria.ui.menu.ui.inquilino.DetalleInquilinoFragment;

import java.util.List;

public class InmuebleAdpter extends RecyclerView.Adapter<InmuebleAdpter.InmuebleViewHolder>{
    public static final int INMUEBLE_DETALLE = 1;
    public static final int INQUILINO_DETALLE = 2;
    public static final int CONTRATO_DETALLE = 3;
    private ViewInmuebleItemBinding bind;
    private List<Inmueble> inmuebleList;
    private int view;
    public InmuebleAdpter(List<Inmueble> inmuebleList, int view) {
        this.inmuebleList = inmuebleList;
        this.view = view;
    }

    @NonNull
    @Override
    public InmuebleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bind = ViewInmuebleItemBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent,false);

        return new InmuebleViewHolder(bind, view);
    }

    @Override
    public void onBindViewHolder(@NonNull InmuebleViewHolder holder, int position) {
        holder.bind(inmuebleList.get(position));
    }

    @Override
    public int getItemCount() {
        return inmuebleList.size();
    }

    public static class InmuebleViewHolder extends RecyclerView.ViewHolder {
        ViewInmuebleItemBinding bind;
        int view;
        public InmuebleViewHolder(ViewInmuebleItemBinding bind, int view) {
            super(bind.getRoot());
            this.bind = bind;
            this.view = view;
        }

        public void bind(Inmueble inmueble){
            bind.tvDireccionItemValor.setText(inmueble.getDireccion());
            bind.tvImporteItemValor.setText("$"+inmueble.getPrecio());

            Util.cargarImagen(bind.getRoot().getContext(), inmueble.getAvatar_Url(), R.drawable.agente_inmobiliario, bind.ivInmueble);

            if(view == INMUEBLE_DETALLE)
            {
                bind.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("idInmueble", inmueble.getId());
                        Navigation.findNavController(bind.getRoot()).navigate(R.id.action_inmueblesFragment_to_agregarInmuebleFragment, bundle);
                    }
                });
            }
            else if(view == INQUILINO_DETALLE)
            {
                bind.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putInt(DetalleInquilinoFragment.IDINQUILINO, inmueble.getIdInquilino());
                        Navigation.findNavController(bind.getRoot()).navigate(R.id.action_inquilinosFragment_to_detalleInquilinoFragment, bundle);
                    }
                });
            }
            else if(view == CONTRATO_DETALLE)
            {
                bind.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putInt(DetalleContratoFragment.IDCONTRATO, inmueble.getIdContrato());
                        Navigation.findNavController(bind.getRoot()).navigate(R.id.action_contratoFragment_to_detalleContratoFragment, bundle);
                    }
                });
            }

        }
    }
}
