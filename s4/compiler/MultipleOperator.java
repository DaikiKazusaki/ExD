package enshud.s4.compiler;

public class MultipleOperator implements Element {
	private String mulitipleOperator;

	public MultipleOperator(String multipleOperator) {
		this.mulitipleOperator = multipleOperator;
	}

	@Override
	public void accept(Visitor visitor) throws SemanticException {
		visitor.visit(this);
	}

}
