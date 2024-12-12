package enshud.s3.checker;

public class Index implements Element {
	private Equation equation;
	
	public Index(Equation equation) {
		this.equation = equation;
	}
	
	public Equation getEquation() {
		return equation;
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
		equation.accept(visitor);
	}
}
