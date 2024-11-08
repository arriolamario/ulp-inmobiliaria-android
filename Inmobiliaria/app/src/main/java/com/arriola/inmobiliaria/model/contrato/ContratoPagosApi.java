package com.arriola.inmobiliaria.model.contrato;

import com.arriola.inmobiliaria.model.ResponseApi;

import java.util.List;

public class ContratoPagosApi extends ResponseApi {
    private List<Pago> data;

    public List<Pago> getData() {
        return data;
    }

    public void setData(List<Pago> data) {
        this.data = data;
    }
}
