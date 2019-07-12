package com.example.inventarioskydata.model;

public class Inventario {
    private String id;
    private String nombre;
    private int cantidad;
    private String armario;
    private String estante;

    public String getArmario() {
        return armario;
    }

    public void setArmario(String armario) {
        this.armario = armario;
    }

    public String getEstante() {
        return estante;
    }

    public void setEstante(String estante) {
        this.estante = estante;
    }

    public Inventario() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return nombre + " cantidad " +cantidad;
    }
}
