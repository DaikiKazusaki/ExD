package enshud.s3.checker;

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
		
		if (variableGroup != null) {
			variableGroup.accept(visitor);
		} 
		if (equationGroup != null) {
			equationGroup.accept(visitor);
		}
	}
}
