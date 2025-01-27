package enshud.s3.checker;

public class AssignStatement implements Node {
    private LeftSide leftSide;
    private Equation equation;
    private String lineNum;

    public AssignStatement(LeftSide leftSide, Equation equation, String lineNum) {
        this.leftSide = leftSide;
        this.equation = equation;
        this.lineNum = lineNum;
    }

    public LeftSide getLeftSide() {
        return leftSide;
    }

    public Equation getEquation() {
        return equation;
    }

    @Override
    public void accept(Visitor visitor) throws SemanticException {
        visitor.visit(this);        
    }

	public String getLineNum() {
		return lineNum;
	}
}
