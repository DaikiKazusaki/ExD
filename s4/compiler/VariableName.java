package enshud.s4.compiler;

public class VariableName implements Element {
	private String variableName;

	public VariableName(String variableName) {
		this.variableName = variableName;
	}

	@Override
	public void accept(Visitor visitor) throws SemanticException {
		visitor.visit(this);
	}

}
