package enshud.s3.checker;

public class LeftSide {
	private Variable variable;

	public LeftSide(Variable variable) {
		this.variable = variable;
	}
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
		variable.accept(visitor);
	}
}
