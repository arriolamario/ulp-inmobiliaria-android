package com.arriola.inmobiliaria.ui.menu.ui.inmueble;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.arriola.inmobiliaria.R;
import com.arriola.inmobiliaria.databinding.ViewInmuebleItemBinding;
import com.arriola.inmobiliaria.model.inmueble.Inmueble;
import com.arriola.inmobiliaria.request.ApiClient;
import com.bumptech.glide.Glide;

import java.util.List;

public class InmuebleAdpter extends RecyclerView.Adapter<InmuebleAdpter.InmuebleViewHolder>{
    private ViewInmuebleItemBinding bind;
    List<Inmueble> inmuebleList;
    public InmuebleAdpter(List<Inmueble> inmuebleList) {
        this.inmuebleList = inmuebleList;
    }

    @NonNull
    @Override
    public InmuebleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bind = ViewInmuebleItemBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent,false);

        return new InmuebleViewHolder(bind);
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
        TextView direccionTextView, tipoTextView, usoTextView;
        ViewInmuebleItemBinding bind;
        public InmuebleViewHolder(ViewInmuebleItemBinding bind) {
            super(bind.getRoot());
            this.bind = bind;
        }

        public void bind(Inmueble inmueble){
            bind.tvDireccion.setText(inmueble.getDireccion());
            bind.tvPrecio.setText("$"+inmueble.getPrecio());
            Glide.with(bind.getRoot().getContext())
                    .load(ApiClient.URLBASE + inmueble.getAvatar_Url())
                    .placeholder(R.drawable.agente_inmobiliario)
                    .error(R.drawable.agente_inmobiliario)
                    .into(bind.ivInmueble);

            bind.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("idInmueble", inmueble.getId());
                    Navigation.findNavController(bind.getRoot()).navigate(R.id.action_inmueblesFragment_to_agregarInmuebleFragment, bundle);
                }
            });
        }
    }
}
