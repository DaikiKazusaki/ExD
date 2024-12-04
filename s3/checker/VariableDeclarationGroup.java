package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class VariableDeclarationGroup implements Element {
	List<VariableName> variableName = new ArrayList<>();
	List<Type> type = new ArrayList<>();
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
		
		for(int i = 0; i < variableName.size(); i++) {
			variableName.get(i).accept(visitor);
			type.get(i).accept(visitor);
		}
	}
}
