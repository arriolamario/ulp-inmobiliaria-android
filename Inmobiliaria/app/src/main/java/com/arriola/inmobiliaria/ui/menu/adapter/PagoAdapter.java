package com.arriola.inmobiliaria.ui.menu.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arriola.inmobiliaria.databinding.ViewInmuebleItemBinding;
import com.arriola.inmobiliaria.databinding.ViewPagoItemBinding;
import com.arriola.inmobiliaria.model.contrato.Pago;
import com.arriola.inmobiliaria.model.inmueble.Inmueble;

import java.text.SimpleDateFormat;
import java.util.List;

public class PagoAdapter extends RecyclerView.Adapter<PagoAdapter.PagoViewHolder>{
    private List<Pago> pagosList;
    private ViewPagoItemBinding bind;
    public PagoAdapter(List<Pago> pagosList) {
        this.pagosList = pagosList;
    }
    @NonNull
    @Override
    public PagoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bind = ViewPagoItemBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent,false);

        return new PagoViewHolder(bind);
    }

    @Override
    public void onBindViewHolder(@NonNull PagoViewHolder holder, int position) {
        holder.bind(pagosList.get(position));
    }

    @Override
    public int getItemCount() {
        return pagosList.size();
    }

    public static class PagoViewHolder extends RecyclerView.ViewHolder {
        private ViewPagoItemBinding bind;
        public PagoViewHolder(ViewPagoItemBinding bind) {
            super(bind.getRoot());
            this.bind = bind;
        }

        public void bind(Pago pago) {
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

            bind.tvNumeroPagoValor.setText(pago.getNumeroPago()+"");
            bind.tvFechaPagoValor.setText(formato.format(pago.getFechaPago()));
            bind.tvImportePagoValor.setText("$"+pago.getImporte());
            bind.tvDetalleValor.setText(pago.getDetalle());
        }
    }
}
