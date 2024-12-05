package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class EquationGroup {
	private List<Equation> equation = new ArrayList<>();

	public EquationGroup(List<Equation> equation) {
		this.equation = equation;
	}
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
		
		for(Equation item: equation) {
			item.accept(visitor);
		}
	}
}
