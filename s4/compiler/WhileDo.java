package enshud.s4.compiler;

public class WhileDo implements Node {
    private Equation equation;
    private ComplexStatement complexStatement;
    private String lineNum;

    public WhileDo(Equation equation, ComplexStatement complexStatement, String lineNum) {
        this.equation = equation;
        this.complexStatement = complexStatement;
        this.lineNum = lineNum;
    }

    public Equation getEquation() {
        return equation;
    }

    public ComplexStatement getComplexStatement() {
        return complexStatement;
    }

    @Override
    public void accept(Visitor visitor) throws SemanticException {
        visitor.visit(this);
    }

	public String getLineNum() {
		return lineNum;
	}
}
