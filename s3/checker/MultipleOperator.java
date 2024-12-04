package enshud.s3.checker;

public class MultipleOperator {
	String operator;
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
	
}
