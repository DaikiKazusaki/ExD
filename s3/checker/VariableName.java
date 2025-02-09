package enshud.s3.checker;

public class VariableName implements Node {
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
