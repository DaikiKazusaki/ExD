package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class VariableNameGroup implements Element {
	private List<VariableName> variableName = new ArrayList<>();
	private String lineNum;
	
	public VariableNameGroup(List<VariableName> variableName, String lineNum) {
		this.variableName = variableName;
		this.lineNum = lineNum;
	}

	public List<VariableName> getVariableNameList() {
		return variableName;
	}
	
	public String getVariableNameGroupLineNum() {
		return lineNum;
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
		for (VariableName item: variableName) {
			item.accept(visitor);
		}
	}
}
