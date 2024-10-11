package org.example.pedidos;

import java.util.ArrayList;
import java.util.List;

public class MainPedidos {
    public static void main(String[] args) {
        Producto monitor = new Producto("Monitor",100.0);
        Producto rato = new Producto("Rato",10.0);
        Producto portatil = new Producto("Portatil",600.0);
        Producto tablet = new Producto("Tablet",400.0);
        Producto teclado = new Producto("Teclado",200.0);

        Cliente cliente1 = new Cliente("Cliente1");
        Cliente cliente2 = new Cliente("Cliente2");
        Cliente cliente3 = new Cliente("Cliente3");
        Cliente cliente4 = new Cliente("Cliente4");
        Cliente cliente5 = new Cliente("Cliente5");

        Pedido pedido1 = new Pedido(cliente1);
        Pedido pedido2 = new Pedido(cliente2);
        Pedido pedido3 = new Pedido(cliente2);
        Pedido pedido4 = new Pedido(cliente3);
        Pedido pedido5 = new Pedido(cliente4);
        Pedido pedido6 = new Pedido(cliente5);

        pedido1.addProducto(monitor)
                .addProducto(rato);
        pedido2.addProducto(portatil)
                .addProducto(tablet);
        pedido3.addProducto(portatil)
                .addProducto(tablet);
        pedido4.addProducto(monitor)
                .addProducto(rato);
        pedido5.addProducto(teclado);
        pedido6.addProducto(monitor)
                .addProducto(rato);

        List<Pedido> pedidos = new ArrayList<>();
        pedidos.add(pedido1);
        pedidos.add(pedido2);
        pedidos.add(pedido3);
        pedidos.add(pedido4);
        pedidos.add(pedido5);
        pedidos.add(pedido6);

        Pedido.grabarPedidos(pedidos);

        List<Pedido> pedidosLeidos = Pedido.leerPedidos();

        pedidosLeidos.forEach(System.out::println);

        Pedido.grabarPedidosXML(pedidosLeidos);


    }
}
