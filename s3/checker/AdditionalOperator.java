package enshud.s3.checker;

public class AdditionalOperator implements Element {
	private String additionalOperator;

	public AdditionalOperator(String additionalOperator) {
		this.additionalOperator = additionalOperator;
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
	
	public String getAdditionalOperator() {
		return additionalOperator;
	}
}
