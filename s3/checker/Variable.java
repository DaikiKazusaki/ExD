package enshud.s3.checker;

public class Variable {
	private NaturalVariable naturalVariable;
	private VariableWithIndex variableWithIndex;
	
	public Variable(NaturalVariable naturalVariable, VariableWithIndex variableWithIndex) {
		this.naturalVariable = naturalVariable;
		this.variableWithIndex = variableWithIndex;
	}
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
		
		if (naturalVariable != null) {
			naturalVariable.accept(visitor);
		} else {
			variableWithIndex.accept(visitor);
		}
	}
}
