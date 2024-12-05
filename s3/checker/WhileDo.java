package enshud.s3.checker;

public class WhileDo {
	private Equation equation;
	private ComplexStatement complexStatement;
	
	public WhileDo(Equation equation, ComplexStatement complexStatement) {
		this.equation = equation;
		this.complexStatement = complexStatement;
	}
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
		
		equation.accept(visitor);
		complexStatement.accept(visitor);
	}
}
