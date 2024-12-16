package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class VariableGroup implements Element {
	private List<Variable> variable = new ArrayList<>();
	
	public VariableGroup(List<Variable> variable) {
		this.variable = variable;
	}
	
	public List<Variable> getVariable(){
		return variable;
	}

	@Override
	public void accept(Visitor visitor) throws SemanticException {
		visitor.visit(this);
		for (Variable item: variable) {
			item.accept(visitor);
		}
	}
}
