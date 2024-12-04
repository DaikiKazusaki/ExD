package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class FormalParameterGroup {
	List<FormalParameterNameGroup> formalParameterNameGroup = new ArrayList<>();
	GeneralType generalType;
	
	
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
		// formalParameterNameGroup.accept(visitor);
		generalType.accept(visitor);
	}
}
