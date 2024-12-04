package enshud.s3.checker;

public class SubprogramDeclaration {
	SubprogramHead subprogramHead;
	VariableDeclaration variableDeclaration;
	ComplexStatement complexStatement;
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
		subprogramHead.accept(visitor);
		variableDeclaration.accept(visitor);
		complexStatement.accept(visitor);
	}
}
