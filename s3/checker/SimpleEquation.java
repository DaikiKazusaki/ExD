package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class SimpleEquation implements Element {
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
	
	@Override
	public void accept(Visitor visitor) {	
		visitor.visit(this);	
		
		if (sign != null) {
			sign.accept(visitor);
		}
		nesessaryTerm.accept(visitor);
		
		for (int i = 0; i < additionalOperator.size(); i++) {
			additionalOperator.get(i).accept(visitor);
			optionalTerm.get(i).accept(visitor);
		}
	}
}
