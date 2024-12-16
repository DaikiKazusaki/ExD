package enshud.s3.checker;

public class Else implements Element {
	private ComplexStatement complexStatement;
	
	public Else(ComplexStatement complexStatement) {
		this.complexStatement = complexStatement;
	}
	
	@Override
	public void accept(Visitor visitor) throws SemanticException {
		visitor.visit(this);
		if (complexStatement != null) {
			complexStatement.accept(visitor);
		}
	}
}
