package enshud.s4.compiler;

public class VariableName implements Element {
    private String variableName;
    private String lineNum;

    public VariableName(String variableName, String lineNum) {
        this.variableName = variableName;
        this.lineNum = lineNum;
    }

    public String getVariableName() {
        return variableName;
    }
    
    public String getLineNum() {
    	return lineNum;
    }

    @Override
    public void accept(Visitor visitor) throws SemanticException {
        visitor.visit(this);
    }
}
