package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class Equation implements Element {
	private List<SimpleEquation> simpleEquation = new ArrayList<>();
	private List<RelationalOperator> relationalOperator = new ArrayList<>();
	
	public Equation(List<SimpleEquation> simpleEquation, List<RelationalOperator> relationalOperator) {
		this.simpleEquation = simpleEquation;
		this.relationalOperator = relationalOperator;
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
		simpleEquation.get(0).accept(visitor);
		for (int i = 0; i < relationalOperator.size(); i++) {
			relationalOperator.get(i).accept(visitor);
			simpleEquation.get(i + 1).accept(visitor);
		}
	}
}
