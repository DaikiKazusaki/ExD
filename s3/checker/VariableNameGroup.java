package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class VariableNameGroup {
	private List<VariableName> variableName = new ArrayList<>();
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
		
		for(VariableName item: variableName) {
			item.accept(visitor);
		}
		
		// visitor.leave();
	}
}
