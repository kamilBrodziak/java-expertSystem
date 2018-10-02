package kamilbrodziak.expertsystem;

import java.util.ArrayList;
import java.util.List;

public class MultipleValue extends Value{
    private List<String> param = new ArrayList<String>();
    private boolean selectionType;

    public MultipleValue(List<String> param, boolean selectionType) {
        this.param = param;
        this.selectionType = selectionType;
    }

    @Override
    public List<String> getInputPattern() {
        return param;
    }

    @Override
    public boolean getSelectionType() {
        return selectionType;
    }

}