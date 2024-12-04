package enshud.s3.checker;

public class Index {
	private Equation equation;
	
	public Index(Equation equation) {
		this.equation = equation;
	}
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
		equation.accept(visitor);
	}
}
