package kamilbrodziak.expertsystem;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class FactParser extends XMLParser{
    private Document doc;
    private FactRepository factRep = new FactRepository();

    public FactRepository getFactRepository() {
        return factRep;
    }


    @Override
    public void loadXmlDocument(String xmlPath) {
        try {
            loadDoc(xmlPath);
            loadFacts();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void loadDoc(String xmlPath) throws Exception{
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        doc = dBuilder.parse(new File(xmlPath));

        doc.getDocumentElement().normalize();
    }

    private void loadFacts() {
        NodeList nodes = doc.getElementsByTagName("Fact");

        for(int i = 0; i < nodes.getLength(); ++i) {
            Node node = nodes.item(i);
            Fact tempF = loadFact(node);
            factRep.addFact(tempF);
        }
    }

    private Fact loadFact(Node node) {
        String tempFactId = node.getAttributes().item(0).getNodeValue();
        String tempFactDesc = node.getFirstChild().getTextContent();
        Fact temp = new Fact(tempFactId, tempFactDesc);
        NodeList choices = node.getLastChild().getChildNodes();
        for(int i = 0; i < choices.getLength(); ++i) {
            Node currNode = choices.item(i);
            boolean choiceType = currNode.getTextContent().equals("true");
            String choiceId = currNode.getAttributes().item(0).getNodeValue();
            temp.setFactValueById(choiceId, choiceType);
        }
        return temp;
    }
}