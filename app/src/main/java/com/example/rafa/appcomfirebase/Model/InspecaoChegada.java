package com.example.rafa.appcomfirebase.Model;

import java.io.Serializable;

public class InspecaoChegada implements Serializable {
    private String id;
    private String idInsp;
    private String data;
    private String km;
    private String hora;
    private String obs;

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdInsp() {
        return idInsp;
    }

    public void setIdInsp(String idInsp) {
        this.idInsp = idInsp;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getKm() {
        return km;
    }

    public void setKm(String km) {
        this.km = km;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
