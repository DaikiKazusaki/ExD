package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class FormalParameterNameGroup {
	private FormalParameterName formalParameterName1;
	private List<FormalParameterName> formalParameterName2 = new ArrayList<>();
	
	public FormalParameterNameGroup(FormalParameterName formalParameterName1, List<FormalParameterName> formalParameterName2) {
		this.formalParameterName1 = formalParameterName1;
		this.formalParameterName2 = formalParameterName2;
	}
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
