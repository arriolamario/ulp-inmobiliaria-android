package com.arriola.inmobiliaria.model.inmueble;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Tipo {
    private int id;
    private String descripcion;

    public Tipo() {
    }

    public Tipo(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }
    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @NonNull
    @Override
    public String toString() {
        return this.descripcion;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Tipo tipo = (Tipo) obj;
        return id == tipo.id;
    }
}
