package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class VariableNameGroup implements Element {
	private VariableName variableName;
	
	public VariableNameGroup(VariableName variableName) {
		this.variableName = variableName;
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
