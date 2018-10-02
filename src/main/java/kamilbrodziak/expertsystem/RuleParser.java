package kamilbrodziak.expertsystem;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class RuleParser extends XMLParser {
    private Document doc;
    private RuleRepository ruleRep = new RuleRepository();

    public RuleRepository getRuleRepository() {
        return ruleRep;
    }

    @Override
    public void loadXmlDocument(String xmlPath) {
        try {
            loadDoc(xmlPath);
            loadQuestions();
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

    private void loadQuestions() {
        NodeList nodes = doc.getElementsByTagName("Rule");
        for(int i = 0; i < nodes.getLength(); ++i) {
            if(nodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element node = (Element) nodes.item(i);
                String tempQuestionId = node.getAttributes().item(0).getNodeValue();
                Answer tempAnswer = loadAnswer(node.getChildNodes().item(3));
                Question tempQ = new Question(tempQuestionId, node.getChildNodes().item(1).getTextContent(), tempAnswer);
                ruleRep.addQuestion(tempQ);
            }
        }
    }

    private Answer loadAnswer(Node node) {
        Answer temp = new Answer();
        NodeList selections = node.getChildNodes();
        for(int i = 0; i < selections.getLength(); ++i) {
            if(selections.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element currNode = (Element) selections.item(i);
                boolean selectionType = currNode.getAttributes().item(0).getNodeValue().equals("true");
                if(currNode.getChildNodes().item(1).getNodeName().equals("SingleValue")) {
                    String param = currNode.getChildNodes().item(1).getAttributes().item(0).getNodeValue();
                    temp.addValue(new SingleValue(param, selectionType));
                } else {
                    String paramsString = currNode.getChildNodes().item(1).getAttributes().item(0).getNodeValue();
                    List<String> params = Arrays.asList(paramsString.split(","));
                    temp.addValue(new MultipleValue(params, selectionType));
                }
            }
        }
        return temp;
    }
}