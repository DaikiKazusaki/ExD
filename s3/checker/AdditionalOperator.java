package enshud.s3.checker;

public class AdditionalOperator {
	String additionalOperator;

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
