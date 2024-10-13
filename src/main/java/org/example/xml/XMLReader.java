package org.example.xml;

import org.w3c.dom.Document;
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
            addNodeToStringBuilder(texto, root, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return texto.toString();
    }

    private void addNodeToStringBuilder(StringBuilder texto, Node node, int depthLevel) {
        if (node.hasChildNodes()) {
            texto.append("\t".repeat(depthLevel))
                    .append(node.getNodeName())
                    .append(": ");
        } else {
            texto.append(node.getTextContent());
        }
        NodeList nodes = node.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node elementChild = nodes.item(i);
            addNodeToStringBuilder(texto, elementChild, depthLevel + 1);
        }
    }

    public Map<String,Integer> countTagTypes(){
        Map<String,Integer> map = new HashMap<>();
        Node root = doc.getDocumentElement();
        updateTagCount(map, root);
        return map;
    }

    public void updateTagCount(Map<String,Integer> map, Node node){
        String tagName = node.getNodeName();
        map.computeIfPresent(tagName, (k,v)->v+1);
        map.putIfAbsent(tagName, 1);
        NodeList children = node.getChildNodes();
        for (int j = 0; j < children.getLength(); j++) {
            if (children.item(j).hasChildNodes()) {
                updateTagCount(map, children.item(j));
            }
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