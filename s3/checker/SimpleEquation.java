package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class SimpleEquation {
	private Sign sign;
	private Term nesessaryTerm;
	private List<AdditionalOperator> additionalOperator = new ArrayList<>();
	private List<Term> optionalTerm = new ArrayList<>();
	
	public SimpleEquation(Sign sign, Term nesessaryTerm, List<AdditionalOperator> additionalOperator, List<Term> term) {
		this.sign = sign;
		this.nesessaryTerm = nesessaryTerm;
		this.additionalOperator = additionalOperator;
		this.optionalTerm = term;
	}
	
	public void accept(Visitor visitor) {	
		visitor.visit(this);	
	}
}
