package org.example;

import org.example.persona.Persona;

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
        personasLeidas.forEach(System.out::println);

        Persona.crearXML(personasLeidas);
    }
}