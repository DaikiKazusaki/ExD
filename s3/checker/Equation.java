package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class Equation {
	private SimpleEquation nesessarySimpleEquation;
	private List<RelationalOperator> relationalOperator = new ArrayList<>();
	private List<SimpleEquation> optionalSimpleEquation = new ArrayList<>();
	
	public Equation(SimpleEquation nesessarySimpleEquation, List<RelationalOperator> relationalOperator, List<SimpleEquation> simpleEquation) {
		this.nesessarySimpleEquation = nesessarySimpleEquation;
		this.relationalOperator = relationalOperator;
		this.optionalSimpleEquation = simpleEquation;
	}
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
