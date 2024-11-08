package com.arriola.inmobiliaria.model.contrato;

import com.arriola.inmobiliaria.model.ResponseApi;

public class ContratoApi extends ResponseApi {
    private Contrato data;

    public Contrato getData() {
        return data;
    }

    public void setData(Contrato data) {
        this.data = data;
    }
}
