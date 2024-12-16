package enshud.s4.compiler;

public class VariableDeclaration implements Element {
	private VariableDeclarationGroup variableDeclarationGroup;

	public VariableDeclaration(VariableDeclarationGroup variableDeclarationGroup) {
		this.variableDeclarationGroup = variableDeclarationGroup;
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}
}
