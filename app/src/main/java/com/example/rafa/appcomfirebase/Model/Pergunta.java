package com.example.rafa.appcomfirebase.Model;

import java.io.Serializable;

public class Pergunta implements Serializable {
    private String id;
    private String pergunta;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }

}
