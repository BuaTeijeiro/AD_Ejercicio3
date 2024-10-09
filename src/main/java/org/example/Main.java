package org.example;

import org.example.persona.Persona;
import org.example.xml.InvalidExtenstionException;
import org.example.xml.XMLReader;

import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        List<Persona> personas = new ArrayList<>();
        personas.add(new Persona("Jose", 25));
        personas.add(new Persona("Julie", 30));
        personas.add(new Persona("Matěj", 35));
        personas.add(new Persona("Bob", 25));

        Persona.guardarPersonas(personas);

        List<Persona> personasLeidas = Persona.leerPersonas();
        System.out.println("Lista de Personas leída del archivo .bin");
        personasLeidas.forEach(System.out::println);

        Persona.crearXML(personasLeidas);

        List<Persona> personasLeidasXML = Persona.leerPersonasXML();
        System.out.println("Lista de Personas leída del archivo .xml");
        System.out.println(personasLeidasXML);

        try{
            XMLReader reader = XMLReader.XMLFactory(Persona.URL_XML);
            System.out.println(reader.read());
        } catch (InvalidExtenstionException e) {
            System.out.println("No se trata de un archivo XML");
        }
    }
}