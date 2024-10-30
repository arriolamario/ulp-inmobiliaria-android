package com.arriola.inmobiliaria.model.inmueble;

import com.arriola.inmobiliaria.model.ResponseApi;

import java.util.List;

public class TiposApi extends ResponseApi {
    private TiposData data;

    public TiposData getData() {
        return data;
    }

    public void setData(TiposData data) {
        this.data = data;
    }

    public class TiposData{
        private List<Tipo> tipo;
        private List<Tipo> uso;

        public List<Tipo> getTipo() {
            return tipo;
        }

        public void setTipo(List<Tipo> tipo) {
            this.tipo = tipo;
        }

        public List<Tipo> getUso() {
            return uso;
        }

        public void setUso(List<Tipo> uso) {
            this.uso = uso;
        }
    }
}
