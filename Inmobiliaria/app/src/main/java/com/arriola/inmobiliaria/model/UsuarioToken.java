package com.arriola.inmobiliaria.model;

import com.arriola.inmobiliaria.request.ApiClient;

public class UsuarioToken {
    private String email;
    private String nombre;
    private String apellido;
    private String avatar_Url;

    public UsuarioToken() {
    }

    public UsuarioToken(String email, String nombre, String apellido, String avatar_Url) {
        this.email = email;
        this.nombre = nombre;
        this.apellido = apellido;
        this.avatar_Url = avatar_Url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getAvatar_Url() {
        return avatar_Url;
    }

    public void setAvatar_Url(String avatar_Url) {
        this.avatar_Url = avatar_Url;
    }
}
