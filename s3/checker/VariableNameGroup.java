package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class VariableNameGroup implements Element {
	private VariableName variableName1; 
	private List<VariableName> variableName2 = new ArrayList<>();
	
	public VariableNameGroup(VariableName variableName1, List<VariableName> variableName2) {
		this.variableName1 = variableName1;
		this.variableName2 = variableName2;
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
