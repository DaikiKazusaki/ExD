package enshud.s3.checker;

public class SubprogramDeclaration {
	private SubprogramHead subprogramHead;
	private VariableDeclaration variableDeclaration;
	private ComplexStatement complexStatement;
	
	public SubprogramDeclaration(SubprogramHead subprogramHead, VariableDeclaration variableDeclaration, ComplexStatement complexStatement) {
		this.subprogramHead = subprogramHead;
		this.variableDeclaration = variableDeclaration;
		this.complexStatement = complexStatement;
	}
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
		subprogramHead.accept(visitor);
		variableDeclaration.accept(visitor);
		complexStatement.accept(visitor);
	}
}
