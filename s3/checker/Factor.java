package enshud.s3.checker;

public class Factor {
	Variable variable;
	Constant constant;
	Equation equation;
	Factor factor;
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
		
		if (variable != null) {
			variable.accept(visitor);
		} else if (constant != null) {
			variable.accept(visitor);
		} else if (equation != null) {
			equation.accept(visitor);
		} else {
			factor.accept(visitor);
		}
	}
}
