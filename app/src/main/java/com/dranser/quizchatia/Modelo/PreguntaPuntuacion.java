package com.dranser.quizchatia.Modelo;

import static com.dranser.quizchatia.Common.Common.categoriaID;

/**
 * Created by Dranser on 08/11/2017.
 */

public class PreguntaPuntuacion {
    private String Pregunta_Puntuacion;
    private String Usuario;
    private String Puntuacion;
    private String CategoriaID;
    private String CategoriaNombre;

    public PreguntaPuntuacion() {
    }

    public PreguntaPuntuacion(String pregunta_Puntuacion, String usuario, String puntuacion, String categoriaID, String categoriaNombre) {
        Pregunta_Puntuacion = pregunta_Puntuacion;
        Usuario = usuario;
        Puntuacion = puntuacion;
        CategoriaID = categoriaID;
        CategoriaNombre = categoriaNombre;
    }

    public String getPregunta_Puntuacion() {
        return Pregunta_Puntuacion;
    }

    public void setPregunta_Puntuacion(String pregunta_Puntuacion) {
        Pregunta_Puntuacion = pregunta_Puntuacion;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }

    public String getPuntuacion() {
        return Puntuacion;
    }

    public void setPuntuacion(String puntuacion) {
        Puntuacion = puntuacion;
    }

    public String getCategoriaID() {
        return CategoriaID;
    }

    public void setCategoriaID(String categoriaId) {
        CategoriaID = categoriaID;
    }

    public String getCategoriaNombre() {
        return CategoriaNombre;
    }

    public void setCategoriaNombre(String categoriaNombre) {
        CategoriaNombre = categoriaNombre;
    }
}
