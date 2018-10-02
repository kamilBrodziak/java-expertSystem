package kamilbrodziak.expertsystem;

import java.util.ArrayList;
import java.util.List;

public class SingleValue extends Value{
    private String param;
    private boolean selectionType;


    public SingleValue(String param, boolean selectionType) {
        this.param = param;
        this.selectionType = selectionType;
    }

    @Override
    public List<String> getInputPattern() {
        List<String> temp = new ArrayList<String>();
        temp.add(param);
        return temp;
    }

    @Override
    public boolean getSelectionType() {
        return selectionType;
    }
    
}