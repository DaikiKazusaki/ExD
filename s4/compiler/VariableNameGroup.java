package enshud.s4.compiler;

import java.util.List;

public class VariableNameGroup implements Node {
    private List<VariableName> variableNameList;
    private String lineNum;

    public VariableNameGroup(List<VariableName> variableNameList, String lineNum) {
        this.variableNameList = variableNameList;
        this.lineNum = lineNum;
    }

    public List<VariableName> getVariableNameList() {
        return variableNameList;
    }
    
    public String getLineNum() {
    	return lineNum;
    }

    @Override
    public void accept(Visitor visitor) throws SemanticException {
        visitor.visit(this);
    }
}
