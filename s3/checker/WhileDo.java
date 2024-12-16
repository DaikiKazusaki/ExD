package enshud.s3.checker;

public class WhileDo implements Element {
	private Equation equation;
	private ComplexStatement complexStatement;
	
	public WhileDo(Equation equation, ComplexStatement complexStatement) {
		this.equation = equation;
		this.complexStatement = complexStatement;
	}
	
	@Override
	public void accept(Visitor visitor) throws SemanticException {
		visitor.visit(this);
		equation.accept(visitor);
		complexStatement.accept(visitor);
	}
}
