package enshud.s3.checker;

public class Equation {
	SimpleEquation nesessarySimpleEquation;
	RelationalOperator relationalOperator;
	SimpleEquation optionalSimpleEquation;
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
		
		nesessarySimpleEquation.accept(visitor);
		if (relationalOperator != null) {
			relationalOperator.accept(visitor);
			optionalSimpleEquation.accept(visitor);
		}
	}
}
