package org.example.pedidos;

import java.io.Serializable;

public class Cliente implements Serializable {
    private static int CURRENT_ID = 1;
    private int id;
    private String nombre;

    public Cliente(String nombre) {
        this.id = CURRENT_ID++;
        this.nombre = nombre;
    }

    public Cliente() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
