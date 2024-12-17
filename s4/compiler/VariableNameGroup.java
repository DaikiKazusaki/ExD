package enshud.s4.compiler;

import java.util.List;

public class VariableNameGroup implements Element {
	private List<VariableName> variableNameList;

	public VariableNameGroup(List<VariableName> variableNameList) {
		this.variableNameList = variableNameList;
	}

	@Override
	public void accept(Visitor visitor) throws SemanticException {
		visitor.visit(this);
	}

}
