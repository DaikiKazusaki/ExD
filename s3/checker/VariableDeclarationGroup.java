package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class VariableDeclarationGroup implements Element {
	private List<VariableName> variableName = new ArrayList<>();
	private List<Type> type = new ArrayList<>();
	
	public VariableDeclarationGroup(List<VariableName> variableName, List<Type> type) {
		this.variableName = variableName;
		this.type = type;
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
		
		for(int i = 0; i < variableName.size(); i++) {
			variableName.get(i).accept(visitor);
			type.get(i).accept(visitor);
		}
	}
}
