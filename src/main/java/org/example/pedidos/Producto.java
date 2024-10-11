package org.example.pedidos;

import java.io.Serializable;

public class Producto implements Serializable{
    private static int CURRENT_ID = 100;
    private int id;
    private String descripcion;
    private double precio;

    public Producto(String descripcion, double precio) {
        this.id = CURRENT_ID++;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    public Producto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String toString() {
        return new StringBuilder("Id: ").append(id)
                .append(" Nome: ")
                .append(getDescripcion())
                .append(" Prezo: ")
                .append(getPrecio())
                .toString();
    }
}
