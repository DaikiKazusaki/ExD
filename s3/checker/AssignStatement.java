package enshud.s3.checker;

public class AssignStatement implements Element {
	private LeftSide leftSide;
	private Equation equation;
	private String lineNum;
	
	public AssignStatement(LeftSide leftSide, Equation equation, String lineNum) {
		this.leftSide = leftSide;
		this.equation = equation;
		this.lineNum = lineNum;
	}
	
	public String getLineNum() {
		return lineNum;
	}
	
	@Override
	public void accept(Visitor visitor) throws SemanticException {
		visitor.visit(this);
		leftSide.accept(visitor);
		equation.accept(visitor);
	}
}
