package com.dranser.quizchatia.Modelo;

/**
 * Created by Dranser on 20/10/2017.
 */

public class Usuario {
    private String nombreUsuario;
    private String email;
    private String contrasena;

    public Usuario() {

    }

    public Usuario(String nombreUsuario, String email, String contraseña) {
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.contrasena = contraseña;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
