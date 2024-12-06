package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class FormalParameterGroup {
	private FormalParameterNameGroup formalParameterNameGroup1;
	private GeneralType generalType1;
	private List<FormalParameterNameGroup> formalParameterNameGroup2 = new ArrayList<>();
	private List<GeneralType> generalType2 = new ArrayList<>();	
	
	public FormalParameterGroup(FormalParameterNameGroup formalParameterNameGroup1, GeneralType generalType1, List<FormalParameterNameGroup> formalParameterNameGroup2, List<GeneralType> generalType2) {
		this.formalParameterNameGroup1 = formalParameterNameGroup1;
		this.generalType1 = generalType1;
		this.formalParameterNameGroup2 = formalParameterNameGroup2;
		this.generalType2 = generalType2;
	}
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
