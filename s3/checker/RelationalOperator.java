package enshud.s3.checker;

public class RelationalOperator {
	private String operator;

	public RelationalOperator(String operator) {
		this.operator = operator;
	}
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
	
	public String getOperator() {
		return operator;
	}
}
