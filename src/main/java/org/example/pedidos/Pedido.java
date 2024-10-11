package org.example.pedidos;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Pedido implements Serializable{
    private static int CURRENT_ID = 1;
    private static final String FILE_URL = "src/main/resources/pedidos.dat";
    private static final String XML_URL = "src/main/resources/pedidos.xml";
    private int id;
    private Cliente cliente;
    private List<Producto> productos = new ArrayList<Producto>();

    public Pedido(Cliente cliente) {
        this.id = CURRENT_ID++;
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

    public List<Producto> getProductos() {
        return productos;
    }

    public Pedido addProducto(Producto producto) {
        productos.add(producto);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder salida = new StringBuilder();
        salida.append("Id: ")
                .append(id)
                .append(" Nome: ")
                .append(cliente.getNombre())
                .append("\n");
        for (Producto producto : getProductos()) {
            salida.append(producto)
                    .append("\n");
        }
        return salida.toString();
    }

    public static void grabarPedidos(List<Pedido> pedidos) {
        try (FileOutputStream fileEscritor = new FileOutputStream(FILE_URL);
             ObjectOutputStream escritor = new ObjectOutputStream(fileEscritor);){
            for (Pedido pedido : pedidos) {
                escritor.writeObject(pedido);
            }
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public static List<Pedido> leerPedidos() {
        List<Pedido> pedidos = new ArrayList<Pedido>();
        try (FileInputStream fileLector = new FileInputStream(FILE_URL);
             ObjectInputStream lector = new ObjectInputStream(fileLector);){
                while (fileLector.available() > 0){
                    Object o = lector.readObject();
                    if (o instanceof Pedido){
                        pedidos.add((Pedido) o);
                    }
                }
        } catch (IOException | ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        return pedidos;
    }

    public static void grabarPedidosXML(List<Pedido> pedidos) {
        try{
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            DOMImplementation domImpl = builder.getDOMImplementation();
            Document document = domImpl.createDocument(null, "pedidos", null);
            document.setXmlVersion("1.0");
            Element raiz = document.getDocumentElement();
            for (Pedido pedido : pedidos) {
                Element pedidoElement = document.createElement("pedido");
                raiz.appendChild(pedidoElement);

                Element idPedidoElement = document.createElement("idPedido");
                pedidoElement.appendChild(idPedidoElement);
                idPedidoElement.appendChild(document.createTextNode(String.valueOf(pedido.getId())));

                Element clienteElement = document.createElement("nomeCliente");
                pedidoElement.appendChild(clienteElement);
                clienteElement.appendChild(document.createTextNode(pedido.getCliente().getNombre()));

                Element productosElement = document.createElement("productos");
                pedidoElement.appendChild(productosElement);
                for (Producto producto : pedido.getProductos()) {
                    Element productoElement = document.createElement("producto");
                    productosElement.appendChild(productoElement);

                    Element idProductoElement = document.createElement("idProducto");
                    productoElement.appendChild(idProductoElement);
                    idProductoElement.appendChild(document.createTextNode(String.valueOf(producto.getId())));

                    Element descripcionElement = document.createElement("descripcion");
                    productoElement.appendChild(descripcionElement);
                    descripcionElement.appendChild(document.createTextNode(producto.getDescripcion()));

                    Element precioElement = document.createElement("precio");
                    productoElement.appendChild(precioElement);
                    precioElement.appendChild(document.createTextNode(String.valueOf(producto.getPrecio())));
                }
            }

            File xml = new File(XML_URL);
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            StreamResult result = new StreamResult(xml);
            DOMSource source = new DOMSource(document);
            transformer.transform(source, result);

        } catch (ParserConfigurationException | TransformerException e){
            System.out.println(e.getMessage());
        }

    }

}
