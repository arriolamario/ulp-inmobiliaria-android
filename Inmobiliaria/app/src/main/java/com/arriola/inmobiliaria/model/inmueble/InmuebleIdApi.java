package com.arriola.inmobiliaria.model.inmueble;

import com.arriola.inmobiliaria.model.ResponseApi;

public class InmuebleIdApi extends ResponseApi {
    private Inmueble data;

    public Inmueble getData() {
        return data;
    }

    public void setData(Inmueble data) {
        this.data = data;
    }
}
