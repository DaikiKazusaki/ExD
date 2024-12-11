package enshud.s3.checker;

public class IfThen {
	private Equation equation;
	private ComplexStatement complexStatement;
	private Else Else;
	
	public IfThen(Equation equation, ComplexStatement complexStatement, Else Else) {
		this.equation = equation;
		this.complexStatement = complexStatement;
		this.Else = Else;
	}
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
		equation.accept(visitor);
		complexStatement.accept(visitor);
		if (Else != null) {
			Else.accept(visitor);
		}
	}
}
