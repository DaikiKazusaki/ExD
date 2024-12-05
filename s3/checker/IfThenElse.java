package enshud.s3.checker;

public class IfThenElse {
	private Equation equation;
	private ComplexStatement complexStatement1;
	private ComplexStatement complexStatement2;
	
	public IfThenElse(Equation equation, ComplexStatement complexStatement1, ComplexStatement complexStatement2) {
		this.equation = equation;
		this.complexStatement1 = complexStatement1;
		this.complexStatement2 = complexStatement2;
	}
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
		
		equation.accept(visitor);
		complexStatement1.accept(visitor);
		complexStatement2.accept(visitor);
	}
}
