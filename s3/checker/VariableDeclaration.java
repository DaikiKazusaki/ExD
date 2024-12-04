package enshud.s3.checker;

public class VariableDeclaration implements Element {
	private VariableDeclarationGroup variableDeclarationGroup;
	
	public VariableDeclaration(VariableDeclarationGroup variableDeclarationGroup) {
		this.variableDeclarationGroup = variableDeclarationGroup;
	}
	
	public VariableDeclarationGroup getVariableDeclarationGroup(VariableDeclarationGroup variableDeclarationGroup) {
		return variableDeclarationGroup;
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
    	accept(visitor);
	}
}
