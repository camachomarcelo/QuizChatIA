package com.dranser.quizchatia.Modelo;

/**
 * Created by Dranser on 11/11/2017.
 */

public class Ranking {
    private String nombreUsuario;
    private long puntuacion;

    public Ranking() {
    }

    public Ranking(String nombreUsuario, long puntuacion) {
        this.nombreUsuario = nombreUsuario;
        this.puntuacion = puntuacion;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public long getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(long puntuacion) {
        this.puntuacion = puntuacion;
    }
}
