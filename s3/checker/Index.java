package enshud.s3.checker;

public class Index implements Element {
	private Equation equation;
	
	public Index(Equation equation) {
		this.equation = equation;
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
		equation.accept(visitor);
	}
}
