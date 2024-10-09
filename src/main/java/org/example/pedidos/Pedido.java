package org.example.pedidos;

import java.io.Serializable;
import java.util.List;

public class Pedido implements Serializable{
    int id;
    Cliente cliente;
    List<Producto> productos;

    public Pedido(int id, Cliente cliente) {
        this.id = id;
        this.cliente = cliente;
    }

    public Pedido() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Producto> getPedidos() {
        return productos;
    }

    public void addProducto(Producto producto) {
        productos.add(producto);
    }
}
