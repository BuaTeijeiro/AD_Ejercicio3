package org.example.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class XMLReader {
    Document doc;

    XMLReader(String fileName) {
        File file = new File(fileName);
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            this.doc = builder.parse(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String read() {
        StringBuilder texto = new StringBuilder();
        try{
            Node root = doc.getDocumentElement();
            addNodesToStringBuilder(texto, root, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return texto.toString();
    }

    private void addNodesToStringBuilder(StringBuilder texto, Node node, int depthLevel) {
        NodeList nodes = node.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node elementChild = nodes.item(i);
            for (int j = 0; j < depthLevel; j++) {
                texto.append("\t");
            }
            if (elementChild.hasChildNodes()) {
                texto.append(elementChild.getNodeName())
                        .append(":");
            } else {
                texto.append(elementChild.getTextContent());
            }
            texto.append("\n");
            addNodesToStringBuilder(texto, elementChild, depthLevel + 1);
        }
    }

    public Map<String,Integer> countTagTypes(){
        Map<String,Integer> map = new HashMap<>();
        Node root = doc.getDocumentElement();
        addChildTags(map, root);
        return map;
    }

    public void addChildTags (Map<String,Integer> map, Node node){
        String tagName = node.getNodeName();
        map.putIfAbsent(tagName, 1);
        map.computeIfPresent(tagName, (k,v)->v+1);
        NodeList children = node.getChildNodes();
        for (int j = 0; j < children.getLength(); j++) {
            addChildTags(map, children.item(j));
        }
    }

    public static XMLReader XMLFactory(String filename) throws InvalidExtenstionException {
        if (filename.endsWith(".xml")) {
            return new XMLReader(filename);
        } else {
            throw new InvalidExtenstionException();
        }
    }
}