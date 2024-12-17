package enshud.s4.compiler;

public class ElseStatement implements Element {
	private ComplexStatement complexStatement;
	
	public ElseStatement(ComplexStatement complexStatement) {
		this.complexStatement = complexStatement;
	}

	@Override
	public void accept(Visitor visitor) throws SemanticException {
		visitor.visit(this);
	}

}
