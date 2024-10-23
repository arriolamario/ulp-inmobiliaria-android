package com.arriola.inmobiliaria.model.inmueble;

public class Inmueble {
    private int id;
    private String direccion;
    private int idTipoUso;
    private Uso uso;
    private int idTipo;
    private Tipo tipo;

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

    public Uso getUso() {
        return uso;
    }

    public void setUso(Uso uso) {
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
}
