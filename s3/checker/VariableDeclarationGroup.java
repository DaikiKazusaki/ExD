package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class VariableDeclarationGroup extends ASTNode implements Element {
	private VariableNameGroup variableNameGroup;
	private Type type;
	
	// private List<Node> childNode = new ArrayList<>();
	
	public VariableDeclarationGroup(VariableNameGroup variableNameGroup, Type type) {
		this.variableNameGroup = variableNameGroup;
		this.type = type;
	}
	
	public void parse() {
		
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
