package enshud.s3.checker;

public class LeftSide implements Element {
	private Variable variable;

	public LeftSide(Variable variable) {
		this.variable = variable;
	}
	
	@Override
	public void accept(Visitor visitor) throws SemanticException {
		visitor.visit(this);
		variable.accept(visitor);
	}
}
