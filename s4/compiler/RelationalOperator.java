package enshud.s4.compiler;

public class RelationalOperator implements Element {
	private String relationalOperator;

	public RelationalOperator(String relationalOperator) {
		this.relationalOperator = relationalOperator;
	}

	@Override
	public void accept(Visitor visitor) throws SemanticException {
		visitor.visit(this);
	}

}
