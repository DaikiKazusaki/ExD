package enshud.s3.checker;

public class AdditionalOperator {
	private String additionalOperator;

	public AdditionalOperator(String additionalOperator) {
		this.additionalOperator = additionalOperator;
	}
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
	
	public String getAdditionalOperator() {
		return additionalOperator;
	}
}
