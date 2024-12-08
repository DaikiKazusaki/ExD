package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class EquationGroup implements Element {
	private Equation equation1;
	private List<Equation> equation2 = new ArrayList<>();

	public EquationGroup(Equation equation1, List<Equation> equation2) {
		this.equation1 = equation1;
		this.equation2 = equation2;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
		equation1.accept(visitor);
		for (Equation item: equation2) {
			item.accept(visitor);
		}
	}
}
