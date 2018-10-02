package kamilbrodziak.expertsystem;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class FactRepository {
    private Map<String, Fact> facts = new LinkedHashMap<String,Fact>();

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
            return (E) facts.values().toArray()[currPos++];
        }
    }
}