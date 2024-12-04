package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class VariableDeclarationGroup implements Element {
	List<VariableName> variableName = new ArrayList<>();
	Type type;
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
		
		for(VariableName item: variableName) {
			item.accept(visitor);
		}
	}
}
