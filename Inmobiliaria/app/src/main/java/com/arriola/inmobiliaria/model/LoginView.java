package com.arriola.inmobiliaria.model;

public class LoginView {
    private String Usuario;
    private String Clave;

    public LoginView() {
    }

    public LoginView(String usuario, String clave) {
        Usuario = usuario;
        Clave = clave;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }

    public String getClave() {
        return Clave;
    }

    public void setClave(String clave) {
        Clave = clave;
    }
}
