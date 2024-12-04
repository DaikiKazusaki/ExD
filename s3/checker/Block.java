package enshud.s3.checker;

public class Block implements Element {
	VariableDeclaration variableDeclaration;
	SubprogramDeclarationGroup subprogramDeclarationGroup;
	
	public Block(VariableDeclaration variableDeclaration, SubprogramDeclarationGroup subprogramDeclarationGroup) {
		this.variableDeclaration = variableDeclaration;
		this.subprogramDeclarationGroup = subprogramDeclarationGroup;
	}
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
		accept(visitor);
	}
}
