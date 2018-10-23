package com.example.rafa.appcomfirebase.Model;

import java.io.Serializable;

public class Carro implements Serializable {
    private String id;
    private String nome;
    private String placa;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    @Override
    public String toString() {
        return getNome() + " - " + getPlaca();
    }
}
