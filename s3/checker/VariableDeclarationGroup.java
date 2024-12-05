package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class VariableDeclarationGroup implements Element {
	private List<VariableNameGroup> variableNameGroup = new ArrayList<>();
	private List<Type> type = new ArrayList<>();
	
	public VariableDeclarationGroup(List<VariableNameGroup> variableNameGroup, List<Type> type) {
		this.variableNameGroup = variableNameGroup;
		this.type = type;
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
		
		for(int i = 0; i < variableNameGroup.size(); i++) {
			variableNameGroup.get(i).accept(visitor);
			type.get(i).accept(visitor);
		}
	}
}
