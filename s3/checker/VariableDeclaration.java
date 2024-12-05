package enshud.s3.checker;

public class VariableDeclaration implements Element {
	private VariableDeclarationGroup variableDeclarationGroup;
	
	public VariableDeclaration(VariableDeclarationGroup variableDeclarationGroup) {
		this.variableDeclarationGroup = variableDeclarationGroup;
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
		
		if (variableDeclarationGroup != null) {
			variableDeclarationGroup.accept(visitor);
		}
	}
}
