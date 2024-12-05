package enshud.s3.checker;

public class InputOutputStatement {
	private VariableGroup variableGroup;
	private EquationGroup equationGroup;
	
	public InputOutputStatement(VariableGroup variableGroup, EquationGroup equationGroup) {
		this.variableGroup = variableGroup;
		this.equationGroup = equationGroup;
	}
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
		
		if (variableGroup != null) {
			variableGroup.accept(visitor);
		} else {
			equationGroup.accept(visitor);
		}
	}
}
