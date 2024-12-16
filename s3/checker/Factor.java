package enshud.s3.checker;

public class Factor implements Element {
	private Variable variable;
	private Constant constant;
	private Equation equation;
	private Factor factor;
	private String lineNum;
	
	public Factor(Variable variable, Constant constant, Equation equation, Factor factor, String lineNum) {
		this.variable = variable;
		this.constant = constant;
		this.equation = equation;
		this.factor = factor;
		this.lineNum = lineNum;
	}
	
	public Variable getVariable() {
		return variable;
	}
	
	public Constant getConstant() {
		return constant;
	}
	
	public Equation getEquation() {
		return equation;
	}
	
	public Factor getFactor() {
		return factor;
	}
	
	public String getLineNum() {
		return lineNum;
	}
	
	@Override
	public void accept(Visitor visitor) throws SemanticException {
		visitor.visit(this);
		
		if (variable != null) {
			variable.accept(visitor);
		} else if (constant != null) {
			constant.accept(visitor);
		} else if (equation != null) {
			equation.accept(visitor);
		} else {
			factor.accept(visitor);
		}
	}
}
