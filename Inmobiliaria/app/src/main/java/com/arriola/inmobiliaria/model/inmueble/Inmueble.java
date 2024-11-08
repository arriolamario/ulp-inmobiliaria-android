package com.arriola.inmobiliaria.model.inmueble;

public class Inmueble {
    private int id;
    private String direccion;
    private int idTipoUso;
    private Tipo uso;
    private int idTipo;
    private Tipo tipo;
    private String avatar_Url;
    private float precio;
    private int ambientes;
    private boolean activo;
    private int idInquilino;
    private int idContrato;

    public Inmueble() {
    }

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getIdTipoUso() {
        return idTipoUso;
    }

    public void setIdTipoUso(int idTipoUso) {
        this.idTipoUso = idTipoUso;
    }

    public Tipo getUso() {
        return uso;
    }

    public void setUso(Tipo uso) {
        this.uso = uso;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public String getAvatar_Url() {
        return avatar_Url;
    }

    public void setAvatar_Url(String avatar_Url) {
        this.avatar_Url = avatar_Url;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getAmbientes() {
        return ambientes;
    }

    public void setAmbientes(int ambientes) {
        this.ambientes = ambientes;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public int getIdInquilino() {
        return idInquilino;
    }

    public void setIdInquilino(int idInquilino) {
        this.idInquilino = idInquilino;
    }

    public int getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(int idContrato) {
        this.idContrato = idContrato;
    }
}
