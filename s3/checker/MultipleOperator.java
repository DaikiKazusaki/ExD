package enshud.s3.checker;

public class MultipleOperator implements Element {
	private String operator;
	
	public MultipleOperator(String operator) {
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
