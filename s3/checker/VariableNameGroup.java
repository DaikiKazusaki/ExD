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
	
	public List<VariableName> getVariableNameGroup() {
		variableName2.add(0, variableName1);		
		return variableName2;
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
		variableName1.accept(visitor);
		for (VariableName item: variableName2) {
			item.accept(visitor);
		}
	}
}
