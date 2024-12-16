package enshud.s3.checker;

public class Block implements Element {
	private VariableDeclaration variableDeclaration;
	private SubprogramDeclarationGroup subprogramDeclarationGroup;
	
	public Block(VariableDeclaration variableDeclaration, SubprogramDeclarationGroup subprogramDeclarationGroup) {
		this.variableDeclaration = variableDeclaration;
		this.subprogramDeclarationGroup = subprogramDeclarationGroup;
	}
	
	@Override
	public void accept(Visitor visitor) throws SemanticException {
		visitor.visit(this);
		variableDeclaration.accept(visitor);
		subprogramDeclarationGroup.accept(visitor);
	}
}
