package enshud.s3.checker;

public class LeftSide {
	String variableName;

	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
