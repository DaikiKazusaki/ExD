package enshud.s3.checker;

public class AssignStatement implements Element {
	private LeftSide leftSide;
	private Equation equation;
	
	public AssignStatement(LeftSide leftSide, Equation equation) {
		this.leftSide = leftSide;
		this.equation = equation;
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
		leftSide.accept(visitor);
		equation.accept(visitor);
	}
}
