package enshud.s3.checker;

public class Factor implements Element {
	private Variable variable;
	private Constant constant;
	private Equation equation;
	private Factor factor;
	
	public Factor(Variable variable, Constant constant, Equation equation, Factor factor) {
		this.variable = variable;
		this.constant = constant;
		this.equation = equation;
		this.factor = factor;
	}
	
	@Override
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
