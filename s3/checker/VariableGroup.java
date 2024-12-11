package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class VariableGroup implements Element {
	private List<Variable> variable = new ArrayList<>();
	
	public VariableGroup(List<Variable> variable) {
		this.variable = variable;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
		for (Variable item: variable) {
			item.accept(visitor);
		}
	}
}
