package enshud.s4.compiler;

import java.util.List;

public class FormalParameterGroup implements Element {
	private List<FormalParameterNameGroup> formalParameterNameGroupList;
	private List<StandardType> standardTypeList;

	public FormalParameterGroup(List<FormalParameterNameGroup> formalParameterNameGroupList, List<StandardType> standardTypeList) {
		this.formalParameterNameGroupList = formalParameterNameGroupList;
		this.standardTypeList = standardTypeList;
	}

	@Override
	public void accept(Visitor visitor) throws SemanticException {
		visitor.visit(this);
	}
}
