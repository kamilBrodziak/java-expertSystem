package kamilbrodziak.expertsystem;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class RuleRepository {
    private Map<String, Question> questions = new LinkedHashMap<String,Question>();

    public void addQuestion(Question question) {
        questions.put(question.getId(), question);
    }

    public Iterator<Question> getIterator() {
        return new QuestionIterator<Question>();
    }

    public class QuestionIterator<E> implements Iterator<E> {
        private int currPos = 0;
        
        public boolean hasNext() {
            return currPos < questions.size();
        }
    
        public E next() {
            return (E) questions.values().toArray()[currPos++];
        }
    }
}