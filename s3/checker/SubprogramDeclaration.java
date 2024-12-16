package enshud.s3.checker;

public class SubprogramDeclaration implements Element {
	private SubprogramHead subprogramHead;
	private VariableDeclaration variableDeclaration;
	private ComplexStatement complexStatement;
	
	public SubprogramDeclaration(SubprogramHead subprogramHead, VariableDeclaration variableDeclaration, ComplexStatement complexStatement) {
		this.subprogramHead = subprogramHead;
		this.variableDeclaration = variableDeclaration;
		this.complexStatement = complexStatement;
	}
	
	@Override
	public void accept(Visitor visitor) throws SemanticException {
		visitor.visit(this);
		subprogramHead.accept(visitor);
		variableDeclaration.accept(visitor);
		complexStatement.accept(visitor);
	}
}
