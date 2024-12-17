package enshud.s4.compiler;

public class AssignStatement implements Element {
	private LeftSide leftSide;
	private Equation equation;
	
	public AssignStatement(LeftSide leftSide, Equation equation) {
		this.leftSide = leftSide;
		this.equation = equation;
	}

	@Override
	public void accept(Visitor visitor) throws SemanticException {
		visitor.visit(this);		
	}

}
