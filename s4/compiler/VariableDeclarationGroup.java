package enshud.s4.compiler;

import java.util.List;

public class VariableDeclarationGroup implements Node {
    private List<VariableNameGroup> variableNameGroupList;
    private List<Type> typeList;

    public VariableDeclarationGroup(List<VariableNameGroup> variableNameGroupList, List<Type> typeList) {
        this.variableNameGroupList = variableNameGroupList;
        this.typeList = typeList;
    }

    public List<VariableNameGroup> getVariableNameGroupList() {
        return variableNameGroupList;
    }

    public List<Type> getTypeList() {
        return typeList;
    }

    @Override
    public void accept(Visitor visitor) throws SemanticException {
        visitor.visit(this);
    }
}
