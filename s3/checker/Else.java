package enshud.s3.checker;

public class Else {
	private ComplexStatement complexStatement;
	
	public Else(ComplexStatement complexStatement) {
		this.complexStatement = complexStatement;
	}
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
		complexStatement.accept(visitor);
	}
}
