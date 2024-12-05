package enshud.s3.checker;

public class AssignStatement {
	private LeftSide leftSide;
	private Equation equation;
	
	public AssignStatement(LeftSide leftSide, Equation equation) {
		this.leftSide = leftSide;
		this.equation = equation;
	}
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
		
		leftSide.accept(visitor);
		equation.accept(visitor);
	}
}
