package com.example.inventarioskydata.model;

public class Inventario {
    private String id;
    private String nombre;
    private int cantidad;
    private String campo;

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

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    @Override
    public String toString() {
        return nombre + " cantidad " +cantidad;
    }
}
