package enshud.s3.checker;

public class Block {
	private VariableDeclaration variableDeclaration;
	private SubprogramDeclarationGroup subprogramDeclarationGroup;
	
	public Block(VariableDeclaration variableDeclaration, SubprogramDeclarationGroup subprogramDeclarationGroup) {
		this.variableDeclaration = variableDeclaration;
		this.subprogramDeclarationGroup = subprogramDeclarationGroup;
	}
	
	public VariableDeclaration getVariableDeclaration() {
		return variableDeclaration;
	}
	
	public SubprogramDeclarationGroup subprogramDeclarationGroup() {
		return subprogramDeclarationGroup;
	}
}
