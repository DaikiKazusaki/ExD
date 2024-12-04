package enshud.s3.checker;

public class RelationalOperator {
	String operator;

	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
	
}
