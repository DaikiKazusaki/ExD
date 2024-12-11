package enshud.s3.checker;

public class Variable implements Element {
	private NaturalVariable naturalVariable;
	private VariableWithIndex variableWithIndex;
	
	public Variable(NaturalVariable naturalVariable, VariableWithIndex variableWithIndex) {
		this.naturalVariable = naturalVariable;
		this.variableWithIndex = variableWithIndex;
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
		
		if (naturalVariable != null) {
			naturalVariable.accept(visitor);
		} else {
			variableWithIndex.accept(visitor);
		}
	}
}
