package kamilbrodziak.expertsystem;

import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import kamilbrodziak.expertsystem.RuleRepository.QuestionIterator;

public class ESProvider {
    private FactParser factParser;
    private RuleParser ruleParser;
    Map<String, Boolean> answers = new HashMap<String,Boolean>();
    
    public ESProvider(FactParser factParser, RuleParser ruleParser) {
        this.factParser = factParser;
        this.ruleParser = ruleParser;

        factParser.loadXmlDocument("Facts.xml");
        ruleParser.loadXmlDocument("Rules.xml");
        System.out.println(ruleParser.getRuleRepository().getIterator().next().getId());
    }

    public void collectAnswers() {
        Iterator<Question> ruleRepIter = ruleParser.getRuleRepository().getIterator();
        Scanner collect = new Scanner(System.in);
        String answer = "";
        while (ruleRepIter.hasNext()) {
            Question currQuestion = ruleRepIter.next();
            System.out.println(currQuestion.getQuestion());
            if (collect.hasNextLine()) {
                answer = collect.next();
            }
            answers.put(currQuestion.getId(), currQuestion.getEvaluatedAnswer(answer));
        }
    }

    public boolean getAnswerByQuestion(String questionId) {
        return answers.get(questionId);
    }

    public String evaluate() {
        Iterator<Fact> factParIter = factParser.getFactRepository().getIterator();
        boolean possible = false;
        while(factParIter.hasNext()) {
            possible = true;
            Fact currFact = factParIter.next();
            for(String id: currFact.getIdSet()) {
                if(currFact.getValueById(id) != getAnswerByQuestion(id)) {
                    possible = false;
                    break;
                }
            }
            if (possible) {
                return currFact.getDescription();
            }
        }
        return "No option found for you";
    }
}