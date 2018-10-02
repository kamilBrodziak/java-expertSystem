package kamilbrodziak.expertsystem;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Fact {
    private String id;
    private String description;
    Map<String, Boolean> factValues = new HashMap<String,Boolean>();

    public Fact(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public Set<String> getIdSet() {
        return factValues.keySet();
    }

    public void setFactValueById(String id, boolean value) {
        factValues.put(id, value);
    }

    public boolean getValueById(String id) {
        return factValues.get(id); 
    }

    public String getDescription() {
        return description;
    }
}