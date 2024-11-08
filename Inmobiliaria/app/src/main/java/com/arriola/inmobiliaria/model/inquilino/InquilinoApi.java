package com.arriola.inmobiliaria.model.inquilino;

import com.arriola.inmobiliaria.model.ResponseApi;

public class InquilinoApi extends ResponseApi {
    private Inquilino data;

    public Inquilino getData() {
        return data;
    }

    public void setData(Inquilino data) {
        this.data = data;
    }
}
