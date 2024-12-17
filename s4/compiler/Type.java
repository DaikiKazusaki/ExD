package enshud.s4.compiler;

public class Type implements Element {
	private StandardType standardType;
	private ArrayType arrayType;

	public Type(StandardType standardType, ArrayType arrayType) {
		this.standardType = standardType;
		this.arrayType = arrayType;
	}

	@Override
	public void accept(Visitor visitor) throws SemanticException {
		visitor.visit(this);
	}

}
