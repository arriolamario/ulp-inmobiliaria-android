package com.arriola.inmobiliaria.model;

public class Propietario {
    private int id;
    private String documento;
    private String nombre;
    private String apellido;
    private String telefonoArea;
    private String telefonoNumero;
    private String email;
    private String direccion;
    private String avatar_Url;
    private String usuario;

    public Propietario() {
    }

    public Propietario(int id, String documento, String nombre, String apellido, String telefonoArea, String telefonoNumero, String email, String direccion, String avatar_Url, String usuario) {
        this.id = id;
        this.documento = documento;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefonoArea = telefonoArea;
        this.telefonoNumero = telefonoNumero;
        this.email = email;
        this.direccion = direccion;
        this.avatar_Url = avatar_Url;
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
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

    public String getTelefonoArea() {
        return telefonoArea;
    }

    public void setTelefonoArea(String telefonoArea) {
        this.telefonoArea = telefonoArea;
    }

    public String getTelefonoNumero() {
        return telefonoNumero;
    }

    public void setTelefonoNumero(String telefonoNumero) {
        this.telefonoNumero = telefonoNumero;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getAvatar_Url() {
        return avatar_Url;
    }

    public void setAvatar_Url(String avatar_Url) {
        this.avatar_Url = avatar_Url;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
