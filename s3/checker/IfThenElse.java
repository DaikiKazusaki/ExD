package enshud.s3.checker;

public class IfThenElse {
	Equation equation;
	ComplexStatement complexStatement1;
	ComplexStatement complexStatement2;
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
		
		equation.accept(visitor);
		complexStatement1.accept(visitor);
		complexStatement2.accept(visitor);
	}
}
