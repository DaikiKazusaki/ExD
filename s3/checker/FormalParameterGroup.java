package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class FormalParameterGroup {
	private FormalParameterNameGroup formalParameterNameGroup;
	private GeneralType generalType;	
	
	public FormalParameterGroup(FormalParameterNameGroup formalParameterNameGroup, GeneralType generalType) {
		this.formalParameterNameGroup = formalParameterNameGroup;
		this.generalType = generalType;
	}
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
