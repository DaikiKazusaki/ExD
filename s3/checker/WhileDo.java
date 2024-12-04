package enshud.s3.checker;

public class WhileDo {
	Equation equation;
	ComplexStatement complexStatement;
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
		
		equation.accept(visitor);
		complexStatement.accept(visitor);
	}
}
