package enshud.s4.compiler;

public class VariableWithIndex implements Element {
	private VariableName variableName;
	private Index index;

	public VariableWithIndex(VariableName variableName, Index index) {
		this.variableName = variableName;
		this.index = index;
	}

	@Override
	public void accept(Visitor visitor) throws SemanticException {
		visitor.visit(this);
	}

}
