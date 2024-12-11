package enshud.s3.checker;

public class RelationalOperator implements Element {
	private String operator;

	public RelationalOperator(String operator) {
		this.operator = operator;
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
	
	public String getOperator() {
		return operator;
	}
}
