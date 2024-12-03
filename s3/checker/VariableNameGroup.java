package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class VariableNameGroup implements Element {
	private List<VariableName> variableName = new ArrayList<>();
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
		
		for(VariableName item: variableName) {
			item.accept(visitor);
		}
		
		// visitor.leave();
	}
}
