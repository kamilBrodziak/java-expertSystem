package kamilbrodziak.expertsystem;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
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
            if(nodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element node = (Element) nodes.item(i);
                Fact tempF = loadFact(node);
                factRep.addFact(tempF);
            }
        }
    }

    private Fact loadFact(Node node) {
        String tempFactId = node.getAttributes().item(0).getNodeValue();
        String tempFactDesc = node.getChildNodes().item(1).getAttributes().item(0).getNodeValue();
        Fact temp = new Fact(tempFactId, tempFactDesc);
        NodeList choices = node.getChildNodes().item(3).getChildNodes();
        for(int i = 0; i < choices.getLength(); ++i) {
            if(choices.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element currNode = (Element) choices.item(i);
                boolean choiceType = currNode.getTextContent().equals("true");
                String choiceId = currNode.getAttributes().item(0).getNodeValue();
                temp.setFactValueById(choiceId, choiceType);
            }
        }
        return temp;
    }
}