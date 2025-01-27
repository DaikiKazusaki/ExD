package enshud.s3.checker;

import java.util.List;

public class VariableGroup implements Node {
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
