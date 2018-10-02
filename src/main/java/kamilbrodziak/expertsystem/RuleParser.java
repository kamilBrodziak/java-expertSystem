package kamilbrodziak.expertsystem;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
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
            Node node = nodes.item(i);
            String tempQuestionId = node.getAttributes().item(0).getNodeValue();
            System.out.println("tesst");
            Answer tempAnswer = loadAnswer(node);
            System.out.println("tesst");
            Question tempQ = new Question(tempQuestionId, node.getFirstChild().getTextContent(), tempAnswer);
            ruleRep.addQuestion(tempQ);
        }
    }

    private Answer loadAnswer(Node node) {
        Answer temp = new Answer();
        NodeList selections = node.getChildNodes();
        for(int i = 0; i < selections.getLength(); ++i) {
            Node currNode = selections.item(i);
            System.out.println(currNode.getNodeName());
            boolean selectionType = currNode.getAttributes().item(0).getNodeValue().equals("true");
            System.out.println("tesat");
            if(currNode.getFirstChild().getNodeName().equals("SingleValue")) {
                String param = currNode.getFirstChild().getAttributes().item(0).getNodeValue();
                temp.addValue(new SingleValue(param, selectionType));
            } else {
                String paramsString = currNode.getFirstChild().getAttributes().item(0).getNodeValue();
                List<String> params = Arrays.asList(paramsString.split(","));
                temp.addValue(new MultipleValue(params, selectionType));

            }
        }
        return temp;
    }
}