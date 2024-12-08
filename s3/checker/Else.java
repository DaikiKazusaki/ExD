package enshud.s3.checker;

public class Else implements Element {
	private ComplexStatement complexStatement;
	
	public Else(ComplexStatement complexStatement) {
		this.complexStatement = complexStatement;
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
		if (complexStatement != null) {
			complexStatement.accept(visitor);
		}
	}
}
