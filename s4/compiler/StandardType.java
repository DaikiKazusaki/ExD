package enshud.s4.compiler;

public class StandardType implements Element {
	private String standardType;

	public StandardType(String standardType) {
		this.standardType = standardType;
	}

	@Override
	public void accept(Visitor visitor) throws SemanticException {
		visitor.visit(this);
	}

}
