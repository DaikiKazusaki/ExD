package enshud.s4.compiler;

import java.util.List;

public class VariableNameGroup implements Node {
    private List<VariableName> variableNameList;

    public VariableNameGroup(List<VariableName> variableNameList) {
        this.variableNameList = variableNameList;
    }

    public List<VariableName> getVariableNameList() {
        return variableNameList;
    }

    @Override
    public void accept(Visitor visitor) throws SemanticException {
        visitor.visit(this);
    }
}
