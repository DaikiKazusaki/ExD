package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class EquationGroup {
	List<Equation> equation = new ArrayList<>();

	public void accept(Visitor visitor) {
		visitor.visit(this);
		
		for(Equation item: equation) {
			item.accept(visitor);
		}
	}
}
