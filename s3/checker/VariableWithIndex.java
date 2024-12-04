package enshud.s3.checker;

public class VariableWithIndex {
	Variable variable;
	Index index;

	public void accept(Visitor visitor) {
		visitor.visit(this);
		
		variable.accept(visitor);
		index.accept(visitor);
	}
	
}
