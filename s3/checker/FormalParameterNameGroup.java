package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class FormalParameterNameGroup implements Element {
	private List<FormalParameterName> formalParameterName = new ArrayList<>();
	
	public FormalParameterNameGroup(List<FormalParameterName> formalParameterName) {
		this.formalParameterName = formalParameterName;
	}
	
	public List<FormalParameterName> getFormalParameterName() {
		return formalParameterName;
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
		for (FormalParameterName item: formalParameterName) {
			item.accept(visitor);
		}
	}
}
