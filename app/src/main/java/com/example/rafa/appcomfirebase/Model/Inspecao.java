package com.example.rafa.appcomfirebase.Model;

import java.io.Serializable;

public class Inspecao implements Serializable {
    private String id;
    private String idCarro;
    private String dataSaida;
    private String horaSaida;
    private String kmSaida;

    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdCarro() {
        return idCarro;
    }

    public void setIdCarro(String idCarro) {
        this.idCarro = idCarro;
    }

    public String getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(String dataSaida) {
        this.dataSaida = dataSaida;
    }

    public String getHoraSaida() {
        return horaSaida;
    }

    public void setHoraSaida(String horaSaida) {
        this.horaSaida = horaSaida;
    }

    public String getKmSaida() {
        return kmSaida;
    }

    public void setKmSaida(String kmSaida) {
        this.kmSaida = kmSaida;
    }


    @Override
    public String toString() {
        return getDataSaida() + " - " + getHoraSaida();
    }
}
