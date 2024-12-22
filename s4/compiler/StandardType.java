package enshud.s4.compiler;

public class StandardType implements Element {
    private String standardType;
    private String lineNum;

    public StandardType(String standardType, String lineNum) {
        this.standardType = standardType;
        this.lineNum = lineNum;
    }

    public String getStandardType() {
        return standardType;
    }
    
    public String getLineNum() {
    	return lineNum;
    }

    @Override
    public void accept(Visitor visitor) throws SemanticException {
        visitor.visit(this);
    }
}
