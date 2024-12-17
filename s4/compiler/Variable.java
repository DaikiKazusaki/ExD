package enshud.s4.compiler;

public class Variable implements Element {
	private NaturalVariable naturalVariable;
	private VariableWithIndex variableWithIndex;

	public Variable(NaturalVariable naturalVariable, VariableWithIndex variableWithIndex) {
		this.naturalVariable = naturalVariable;
		this.variableWithIndex = variableWithIndex;
	}

	@Override
	public void accept(Visitor visitor) throws SemanticException {
		visitor.visit(this);
	}

}
