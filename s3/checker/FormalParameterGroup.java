package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class FormalParameterGroup {
	List<FormalParameterNameGroup> formalParameterNameGroup = new ArrayList<>();
	List<GeneralType> generalType = new ArrayList<>();	
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
		for (int i = 0; i < formalParameterNameGroup.size(); i++) {
			formalParameterNameGroup.get(i).accept(visitor);
			generalType.get(i).accept(visitor);
		}
	}
}
