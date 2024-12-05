package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class FormalParameterGroup {
	private List<FormalParameterNameGroup> formalParameterNameGroup = new ArrayList<>();
	private List<GeneralType> generalType = new ArrayList<>();	
	
	public FormalParameterGroup(List<FormalParameterNameGroup> formalParameterNameGroup, List<GeneralType> generalType) {
		this.formalParameterNameGroup = formalParameterNameGroup;
		this.generalType = generalType;
	}
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
		for (int i = 0; i < formalParameterNameGroup.size(); i++) {
			formalParameterNameGroup.get(i).accept(visitor);
			generalType.get(i).accept(visitor);
		}
	}
}
