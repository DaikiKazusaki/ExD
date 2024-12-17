package enshud.s4.compiler;

import java.util.List;

public class Equation implements Element  {
	private List<SimpleEquation> simpleEquationList;
	private List<RelationalOperator> relatioinalOperatorList;

	public Equation(List<SimpleEquation> simpleEquationList, List<RelationalOperator> relationalOperatorList) {
		this.simpleEquationList = simpleEquationList;
		this.relatioinalOperatorList = relationalOperatorList;
	}

	@Override
	public void accept(Visitor visitor) throws SemanticException {
		visitor.visit(this);
	}

}
