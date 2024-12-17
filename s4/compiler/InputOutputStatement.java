package enshud.s4.compiler;

public class InputOutputStatement implements Element {
	private VariableGroup variableGroup;
	private EquationGroup equationGroup;	

	public InputOutputStatement(VariableGroup variableGroup, EquationGroup equationGroup) {
		this.variableGroup = variableGroup;
		this.equationGroup = equationGroup;
	}

	@Override
	public void accept(Visitor visitor) throws SemanticException {
		visitor.visit(this);
	}

}
