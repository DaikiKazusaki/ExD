package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class VariableGroup implements Element {
	private Variable variable1;
	private List<Variable> variable2 = new ArrayList<>();
	
	public VariableGroup(Variable variable1, List<Variable> variable2) {
		this.variable1 = variable1;
		this.variable2 = variable2;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
		variable1.accept(visitor);
		for (Variable item: variable2) {
			item.accept(visitor);
		}
	}
}
