package enshud.s3.checker;

public class VariableDeclaration implements Element {
	VariableDeclarationGroup variableDeclarationGroup;
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
		
		if (variableDeclarationGroup != null) {
			variableDeclarationGroup.accept(visitor);
		}
	}
}
