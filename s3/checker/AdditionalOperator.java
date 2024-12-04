package enshud.s3.checker;

public class AdditionalOperator {
	String additionalOperator;

	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
