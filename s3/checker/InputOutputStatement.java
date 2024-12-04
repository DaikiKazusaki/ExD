package enshud.s3.checker;

public class InputOutputStatement {
	VariableGroup variableGroup;
	EquationGroup equationGroup;
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
		
		if (variableGroup != null) {
			variableGroup.accept(visitor);
		} else {
			equationGroup.accept(visitor);
		}
	}
}
