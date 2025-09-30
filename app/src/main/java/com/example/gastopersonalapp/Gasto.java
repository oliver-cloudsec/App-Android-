package com.example.gastopersonalapp;

public class Gasto {
    private String descripcion;
    private double monto;

    public Gasto(String descripcion, double monto) {
        this.descripcion = descripcion;
        this.monto = monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getMonto() {
        return monto;
    }
}
