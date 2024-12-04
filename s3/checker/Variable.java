package enshud.s3.checker;

public class Variable {
	NaturalVariable naturalVariable;
	VariableWithIndex variableWithIndex;
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
		
		if (naturalVariable != null) {
			naturalVariable.accept(visitor);
		} else {
			variableWithIndex.accept(visitor);
		}
	}
}
