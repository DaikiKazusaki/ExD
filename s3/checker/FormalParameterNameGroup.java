package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class FormalParameterNameGroup {
	private FormalParameterName formalParameterName;
	
	public FormalParameterNameGroup(FormalParameterName formalParameterName2) {
		this.formalParameterName = formalParameterName2;
	}
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
