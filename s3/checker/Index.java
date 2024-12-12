package enshud.s3.checker;

public class Index implements Element {
	private Equation equation;
	private String lineNum;
	
	public Index(Equation equation, String lineNum) {
		this.equation = equation;
		this.lineNum = lineNum;
	}
	
	public Equation getEquation() {
		return equation;
	}
	
	public String getLineNum() {
		return lineNum;
	}
	
	@Override
	public void accept(Visitor visitor) throws SemanticException {
		visitor.visit(this);
		equation.accept(visitor);
	}
}
