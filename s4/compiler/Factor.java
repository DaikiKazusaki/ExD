package enshud.s4.compiler;

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
	public void accept(Visitor visitor) throws SemanticException {
		visitor.visit(this);
	}

}
