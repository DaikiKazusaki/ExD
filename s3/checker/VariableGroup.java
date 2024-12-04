package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class VariableGroup {
	List<Variable> variable = new ArrayList<>();

	public void accept(Visitor visitor) {
		visitor.visit(this);
		
		for(Variable item: variable) {
			item.accept(visitor);
		}
	}
}
