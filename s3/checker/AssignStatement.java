package enshud.s3.checker;

public class AssignStatement {
	LeftSide leftSide;
	Equation equation;
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
		
		leftSide.accept(visitor);
		equation.accept(visitor);
	}
}
