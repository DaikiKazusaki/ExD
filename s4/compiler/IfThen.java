package enshud.s4.compiler;

public class IfThen implements Node {
    private Equation equation;
    private ComplexStatement complexStatement;
    private ElseStatement elseStatement;
    private String lineNum;

    public IfThen(Equation equation, ComplexStatement complexStatement, ElseStatement elseStatement, String lineNum) {
        this.equation = equation;
        this.complexStatement = complexStatement;
        this.elseStatement = elseStatement;
        this.lineNum = lineNum;
    }

    public Equation getEquation() {
        return equation;
    }

    public ComplexStatement getComplexStatement() {
        return complexStatement;
    }

    public ElseStatement getElseStatement() {
        return elseStatement;
    }

    @Override
    public void accept(Visitor visitor) throws SemanticException {
        visitor.visit(this);
    }

	public String getLineNum() {
		return lineNum;
	}
}
