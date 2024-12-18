package enshud.s4.compiler;

import java.util.List;

public class VariableGroup implements Element {
    private List<Variable> variableList;

    public VariableGroup(List<Variable> variableList) {
        this.variableList = variableList;
    }

    public List<Variable> getVariableList() {
        return variableList;
    }

    @Override
    public void accept(Visitor visitor) throws SemanticException {
        visitor.visit(this);
    }
}
