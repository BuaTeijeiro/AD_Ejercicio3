package org.example.persona;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Persona implements Serializable {
    private String nombre;
    private int edad;
    private static final String URL_FILE = "src/main/resources/personas.bin";
    public static final String URL_XML = "src/main/resources/personas.xml";

    public Persona(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
    }

    public Persona(){

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String toString() {
        return nombre + ": " + edad;
    }

    public static void guardarPersonas(List<Persona> personas) {
        try (ObjectOutputStream escritor = new ObjectOutputStream(new FileOutputStream(URL_FILE))) {
            for (Persona persona : personas) {
                escritor.writeObject(persona);
            }
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public static List<Persona> leerPersonas() {
        List<Persona> personas = new ArrayList<>();
        try(ObjectInputStream lector = new ObjectInputStream(new FileInputStream(URL_FILE))){
            Object o;
            while (true){
                o = lector.readObject();
                if(o instanceof Persona){
                    personas.add((Persona)o);
                }
            }
        } catch (EOFException e){
            System.out.println("Lectura completada");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        return personas;
    }

    public static void crearXML(List<Persona> personas){
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation domImpl = builder.getDOMImplementation();
            Document document = domImpl.createDocument(null, "personas", null);
            document.setXmlVersion("1.0");
            Element raiz = document.getDocumentElement();

            for (Persona persona : personas) {
                Element personaElement = document.createElement("persona");
                raiz.appendChild(personaElement);

                Element nombreElement = document.createElement("nombre");
                personaElement.appendChild(nombreElement);
                nombreElement.appendChild(document.createTextNode(persona.getNombre()));

                Element edadElement = document.createElement("edad");
                personaElement.appendChild(edadElement);
                edadElement.appendChild(document.createTextNode(String.valueOf(persona.getEdad())));
            }

            File f = new File(URL_XML);
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            StreamResult result = new StreamResult(f);
            DOMSource source = new DOMSource(document);
            transformer.transform(source,result);
        } catch (ParserConfigurationException | TransformerException e){
            System.out.println(e.getMessage());
        }
    }

    public static List<Persona> leerPersonasXML() {
        List<Persona> personas = new ArrayList<>();
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(new File(URL_XML));
            NodeList nodos = document.getElementsByTagName("persona");
            for (int i = 0; i < nodos.getLength(); i++) {
                Node persona = nodos.item(i);
                Element personaElement = (Element) persona;
                String nombre = personaElement.getElementsByTagName("nombre").item(0).getTextContent();
                String edad = personaElement.getElementsByTagName("edad").item(0).getTextContent();
                personas.add(new Persona(nombre, Integer.parseInt(edad)));
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return personas;
    }
}
