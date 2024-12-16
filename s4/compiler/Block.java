package enshud.s4.compiler;

public class Block implements Element {
	private VariableDeclaration variableDeclaration;
	private SubprogramDeclarationGroup subprogramDeclarationGroup;
	
	public Block(VariableDeclaration variableDeclaration, SubprogramDeclarationGroup subprogramDeclarationGroup) {
		this.variableDeclaration = variableDeclaration;
		this.subprogramDeclarationGroup = subprogramDeclarationGroup;
	}
	
	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}
}
