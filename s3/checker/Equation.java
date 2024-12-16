package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class Equation implements Element {
	private SimpleEquation simpleEquation;
	private List<RelationalOperator> relationalOperator = new ArrayList<>();
	private List<SimpleEquation> simpleEquationList = new ArrayList<>();
	
	public Equation(SimpleEquation simpleEquation, List<RelationalOperator> relationalOperator, List<SimpleEquation> simpleEquationList) {
		this.simpleEquation = simpleEquation;
		this.simpleEquationList = simpleEquationList;
		this.relationalOperator = relationalOperator;
	}
	
	public SimpleEquation getSimpleEquation(){
		return simpleEquation;
	}
	
	public RelationalOperator getRelationalOperator(){
		return relationalOperator.get(0);
	}
	
	@Override
	public void accept(Visitor visitor) throws SemanticException {
		visitor.visit(this);
		simpleEquation.accept(visitor);
		for (int i = 0; i < relationalOperator.size(); i++) {
			relationalOperator.get(i).accept(visitor);
			simpleEquationList.get(i).accept(visitor);
		}
	}
}
