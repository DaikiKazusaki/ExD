package enshud.s3.checker;

public class Variable implements Node {
    private NaturalVariable naturalVariable;
    private VariableWithIndex variableWithIndex;
    private String lineNum;

    public Variable(NaturalVariable naturalVariable, VariableWithIndex variableWithIndex, String lineNum) {
        this.naturalVariable = naturalVariable;
        this.variableWithIndex = variableWithIndex;
        this.lineNum = lineNum;
    }

    public NaturalVariable getNaturalVariable() {
        return naturalVariable;
    }

    public VariableWithIndex getVariableWithIndex() {
        return variableWithIndex;
    }

    @Override
    public void accept(Visitor visitor) throws SemanticException {
        visitor.visit(this);
    }

	public String getLineNum() {
		return lineNum;
	}
}
