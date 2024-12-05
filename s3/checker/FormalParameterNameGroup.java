package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class FormalParameterNameGroup {
	private List<FormalParameterName> formalParameterName = new ArrayList<>();
	
	public FormalParameterNameGroup(List<FormalParameterName> formalParameterName) {
		this.formalParameterName = formalParameterName;
	}
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
		
		for(FormalParameterName item: formalParameterName) {
			item.accept(visitor);
		}
	}
}
