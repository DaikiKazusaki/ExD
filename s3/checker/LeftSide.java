package enshud.s3.checker;

public class LeftSide {
	private Variable variableName;

	public LeftSide(Variable variable) {
		this.variableName = variable;
	}
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
		variableName.accept(visitor);
	}
}
