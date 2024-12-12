package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class FormalParameterGroup implements Element {
	private List<FormalParameterNameGroup> formalParameterNameGroup = new ArrayList<>();
	private List<GeneralType> generalType = new ArrayList<>();	
	
	public FormalParameterGroup(List<FormalParameterNameGroup> formalParameterNameGroup, List<GeneralType> generalType) {
		this.formalParameterNameGroup = formalParameterNameGroup;
		this.generalType = generalType;
	}
	
	public List<FormalParameterNameGroup> getFormalParameterNameGroup() {
		return formalParameterNameGroup;
	}
	
	public List<GeneralType> getGeneralType() {
		return generalType;
	}
	
	@Override
	public void accept(Visitor visitor) throws SemanticException {
		visitor.visit(this);
		for (int i = 0; i < formalParameterNameGroup.size(); i++) {
			formalParameterNameGroup.get(i).accept(visitor);
			generalType.get(i).accept(visitor);
		}
	}
}
