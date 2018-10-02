package kamilbrodziak.expertsystem;

import java.util.List;
import java.util.ArrayList;

public class Answer {
    List<Value> values = new ArrayList<Value>();

    public boolean evaluateAnswerByInput(String input) throws Exception{
        for(Value value: values) {
            for(String param: value.getInputPattern()) {
                if(param.equals(input)) {
                    return value.getSelectionType();
                }
            }
        }
        throw new Exception("No such option to choose.");
    }

    public void addValue(Value value) {
        values.add(value);
    }
}