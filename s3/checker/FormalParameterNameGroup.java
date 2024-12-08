package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class FormalParameterNameGroup implements Element {
	private FormalParameterName formalParameterName1;
	private List<FormalParameterName> formalParameterName2 = new ArrayList<>();
	
	public FormalParameterNameGroup(FormalParameterName formalParameterName1, List<FormalParameterName> formalParameterName2) {
		this.formalParameterName1 = formalParameterName1;
		this.formalParameterName2 = formalParameterName2;
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
		formalParameterName1.accept(visitor);
		for (FormalParameterName item: formalParameterName2) {
			item.accept(visitor);
		}
	}
}
