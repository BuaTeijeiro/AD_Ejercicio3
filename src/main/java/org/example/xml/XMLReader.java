package org.example.xml;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class XMLReader {
    File archivo;

    XMLReader(String archivo) {
        this.archivo = new File(archivo);
    }

    public String read() {
        StringBuilder texto = new StringBuilder();
        try{
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(archivo);
            doc.getDocumentElement().normalize();
            texto.append(doc.getDocumentElement().getTextContent());
        } catch (Exception e) {
            e.printStackTrace();
        }



        return texto.toString();
    }

    public static XMLReader XMLFactory(String filename) throws InvalidExtenstionException {
        if (filename.endsWith(".xml")) {
            return new XMLReader(filename);
        } else {
            throw new InvalidExtenstionException();
        }
    }
}
