package enshud.s4.compiler;

public class IfThen implements Element {
	private Equation equation;
	private ComplexStatement complexStatement;
	private ElseStatement elseStatement;

	public IfThen(Equation equation, ComplexStatement complexStatement, ElseStatement elseStatement) {
		this.equation = equation;
		this.complexStatement = complexStatement;
		this.elseStatement = elseStatement;
	}

	@Override
	public void accept(Visitor visitor) throws SemanticException {
		visitor.visit(this);
	}

}
