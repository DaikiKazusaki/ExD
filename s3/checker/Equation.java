package enshud.s3.checker;

public class Equation {
	private SimpleEquation nesessarySimpleEquation;
	private RelationalOperator relationalOperator;
	private SimpleEquation optionalSimpleEquation;
	
	public Equation(SimpleEquation nesessarySimpleEquation, RelationalOperator relationalOperator, SimpleEquation optionalSimpleEquation) {
		this.nesessarySimpleEquation = nesessarySimpleEquation;
		this.relationalOperator = relationalOperator;
		this.optionalSimpleEquation = optionalSimpleEquation;
	}
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
		
		nesessarySimpleEquation.accept(visitor);
		if (relationalOperator != null) {
			relationalOperator.accept(visitor);
			optionalSimpleEquation.accept(visitor);
		}
	}
}
