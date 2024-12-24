package enshud.s4.compiler;

public class AdditionalOperator implements Node {
	private String additionalOperator;
	
	public AdditionalOperator(String additionalOperator) {
		this.additionalOperator = additionalOperator;
	}

	public String getAdditionalOperator() {
        return additionalOperator;
    }
	
	@Override
	public void accept(Visitor visitor) throws SemanticException {
		visitor.visit(this);		
	}
	
}
