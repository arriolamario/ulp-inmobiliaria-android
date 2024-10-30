package com.arriola.inmobiliaria.model.inmueble;

import com.arriola.inmobiliaria.model.ResponseApi;

import java.util.List;

public class InmuebleApi extends ResponseApi {
    private List<Inmueble> data;

    public List<Inmueble> getData() {
        return data;
    }

    public void setData(List<Inmueble> data) {
        this.data = data;
    }
}
