package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class FormalParameterGroup implements Element {
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
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
		formalParameterNameGroup1.accept(visitor);
		generalType1.accept(visitor);
		for (int i = 0; i < formalParameterNameGroup2.size(); i++) {
			formalParameterNameGroup2.get(i).accept(visitor);
			generalType2.get(i).accept(visitor);
		}
	}
}
