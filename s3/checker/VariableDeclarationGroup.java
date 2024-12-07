package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class VariableDeclarationGroup extends ASTNode implements Element {
	private VariableNameGroup variableNameGroup1;
	private Type type1;
	private List<VariableNameGroup> variableNameGroup2 = new ArrayList<>();
	private List<Type> type2 = new ArrayList<>();
	
	// private List<Node> childNode = new ArrayList<>();
	
	public VariableDeclarationGroup(VariableNameGroup variableNameGroup1, Type type1, List<VariableNameGroup> variableNameGroup2, List<Type> type2) {
		this.variableNameGroup1 = variableNameGroup1;
		this.type1 = type1;
		this.variableNameGroup2 = variableNameGroup2;
		this.type2 = type2;
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
