package com.dranser.quizchatia.Modelo;

/**
 * Created by Dranser on 08/11/2017.
 */

public class Pregunta {
    private String Pregunta, RespuestaA, RespuestaB, RespuestaC, RespuestaD, RespuestaCorrecta, CategoriaID, IsImageQuestion;

    public Pregunta() {
    }

    public Pregunta(String pregunta, String respuestaA, String respuestaB, String respuestaC, String respuestaD, String respuestaCorrecta, String categoriaID, String isImageQuestion) {
        Pregunta = pregunta;
        RespuestaA = respuestaA;
        RespuestaB = respuestaB;
        RespuestaC = respuestaC;
        RespuestaD = respuestaD;
        RespuestaCorrecta = respuestaCorrecta;
        CategoriaID = categoriaID;
        IsImageQuestion = isImageQuestion;
    }

    public String getPregunta() {
        return Pregunta;
    }

    public void setPregunta(String pregunta) {
        Pregunta = pregunta;
    }

    public String getRespuestaA() {
        return RespuestaA;
    }

    public void setRespuestaA(String respuestaA) {
        RespuestaA = respuestaA;
    }

    public String getRespuestaB() {
        return RespuestaB;
    }

    public void setRespuestaB(String respuestaB) {
        RespuestaB = respuestaB;
    }

    public String getRespuestaC() {
        return RespuestaC;
    }

    public void setRespuestaC(String respuestaC) {
        RespuestaC = respuestaC;
    }

    public String getRespuestaD() {
        return RespuestaD;
    }

    public void setRespuestaD(String respuestaD) {
        RespuestaD = respuestaD;
    }

    public String getRespuestaCorrecta() {
        return RespuestaCorrecta;
    }

    public void setRespuestaCorrecta(String respuestaCorrecta) {
        RespuestaCorrecta = respuestaCorrecta;
    }

    public String getCategoriaID() {
        return CategoriaID;
    }

    public void setCategoriaID(String categoriaID) {
        CategoriaID = categoriaID;
    }

    public String getIsImageQuestion() {
        return IsImageQuestion;
    }

    public void setIsImageQuestion(String isImageQuestion) {
        IsImageQuestion = isImageQuestion;
    }
}
