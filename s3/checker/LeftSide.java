package enshud.s3.checker;

public class LeftSide implements Element {
	private Variable variable;
	private String lineNum;

	public LeftSide(Variable variable, String lineNum) {
		this.variable = variable;
		this.lineNum = lineNum;
	}
	
	public Variable getVariable() {
		return variable;
	}
	
	public String getLineNum() {
		return lineNum;
	}
	
	@Override
	public void accept(Visitor visitor) throws SemanticException {
		visitor.visit(this);
		variable.accept(visitor);
	}
}
