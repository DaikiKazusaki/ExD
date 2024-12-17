package enshud.s4.compiler;

public class Index implements Element {
	private Equation equation;

	public Index(Equation equation) {
		this.equation = equation;
	}

	@Override
	public void accept(Visitor visitor) throws SemanticException {
		visitor.visit(this);
	}

}
