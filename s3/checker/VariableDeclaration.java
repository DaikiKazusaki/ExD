package enshud.s3.checker;

public class VariableDeclaration implements Element {
	private VariableDeclarationGroup variableDeclarationGroup;
	
	public VariableDeclaration(VariableDeclarationGroup variableDeclarationGroup) {
		this.variableDeclarationGroup = variableDeclarationGroup;
<<<<<<< HEAD
	}
	
	public VariableDeclarationGroup getVariableDeclarationGroup(VariableDeclarationGroup variableDeclarationGroup) {
		return variableDeclarationGroup;
	}
=======
  }
>>>>>>> bc25f4ce43552e281612146553c38f016aed7261
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
    	accept(visitor);
	}
}
