package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class EquationGroup {
	private Equation equation1;
	private Equation equation2;

	public EquationGroup(Equation equation1, Equation equation2) {
		this.equation1 = equation1;
		this.equation2 = equation2;
	}

	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
