package com.arriola.inmobiliaria.model;

import java.util.Date;

public class Token {
    private String token;
    private Date expiracion;
    private UsuarioToken usuario;

    public Token() {
    }

    public Token(String token, Date expiracion, UsuarioToken usuario) {
        this.token = token;
        this.expiracion = expiracion;
        this.usuario = usuario;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpiracion() {
        return expiracion;
    }

    public void setExpiracion(Date expiracion) {
        this.expiracion = expiracion;
    }

    public UsuarioToken getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioToken usuario) {
        this.usuario = usuario;
    }

    public String getTokenHeader(){
        return "Bearer " + token;
    }
}
