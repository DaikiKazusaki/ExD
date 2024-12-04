package enshud.s3.checker;

public class Block implements Element {
	VariableDeclaration variableDeclaration;
	SubprogramDeclarationGroup subprogramDeclarationGroup;
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
		variableDeclaration.accept(visitor);
		subprogramDeclarationGroup.accept(visitor);
	}
}
