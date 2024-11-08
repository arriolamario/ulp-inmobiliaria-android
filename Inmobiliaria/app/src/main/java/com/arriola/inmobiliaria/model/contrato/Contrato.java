package com.arriola.inmobiliaria.model.contrato;

import com.arriola.inmobiliaria.model.inmueble.Inmueble;
import com.arriola.inmobiliaria.model.inquilino.Inquilino;

import java.util.Date;

public class Contrato {
    private int id;
    private Date fechaDesde;
    private Date fechaHasta;
    private float montoAlquiler;
    private Inquilino inquilino;
    private Inmueble inmueble;

    public Contrato() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public float getMontoAlquiler() {
        return montoAlquiler;
    }

    public void setMontoAlquiler(float montoAlquiler) {
        this.montoAlquiler = montoAlquiler;
    }

    public Inquilino getInquilino() {
        return inquilino;
    }

    public void setInquilino(Inquilino inquilino) {
        this.inquilino = inquilino;
    }

    public Inmueble getInmueble() {
        return inmueble;
    }

    public void setInmueble(Inmueble inmueble) {
        this.inmueble = inmueble;
    }
}
