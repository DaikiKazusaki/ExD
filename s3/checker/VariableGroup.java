package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class VariableGroup {
	private Variable variable1;
	private Variable variable2;
	
	public VariableGroup(Variable variable1, Variable variable2) {
		this.variable1 = variable1;
		this.variable2 = variable2;
	}

	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
