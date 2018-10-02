package kamilbrodziak.expertsystem;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FactRepository {
    private Map<String, Fact> facts = new HashMap<String,Fact>();

    public void addFact(Fact fact) {
        facts.put(fact.getDescription(), fact);
    }

    public Iterator<Fact> getIterator() {
        return new factIterator<Fact>();
    }

    private class factIterator<E> implements Iterator<E> {
        private int currPos = 0;
        
        public boolean hasNext() {
            return currPos < facts.size();
        }
    
        public E next() {
            return (E) facts.get(currPos++);
        }
    }
}