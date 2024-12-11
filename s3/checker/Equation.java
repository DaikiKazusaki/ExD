package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class Equation implements Element {
	private SimpleEquation nesessarySimpleEquation;
	private List<RelationalOperator> relationalOperator = new ArrayList<>();
	private List<SimpleEquation> optionalSimpleEquation = new ArrayList<>();
	
	public Equation(SimpleEquation nesessarySimpleEquation, List<RelationalOperator> relationalOperator, List<SimpleEquation> simpleEquation) {
		this.nesessarySimpleEquation = nesessarySimpleEquation;
		this.relationalOperator = relationalOperator;
		this.optionalSimpleEquation = simpleEquation;
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
		nesessarySimpleEquation.accept(visitor);
		for (int i = 0; i < relationalOperator.size(); i++) {
			relationalOperator.get(i).accept(visitor);
			optionalSimpleEquation.get(i).accept(visitor);
		}
	}
}
