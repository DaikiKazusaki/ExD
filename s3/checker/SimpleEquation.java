package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class SimpleEquation implements Element {
	private Sign sign;
	private Term term ;
	private List<AdditionalOperator> additionalOperator = new ArrayList<>();
	private List<Term> termList = new ArrayList<>();
	
	public SimpleEquation(Sign sign, Term term, List<AdditionalOperator> additionalOperator, List<Term> termList) {
		this.sign = sign;
		this.term = term;
		this.additionalOperator = additionalOperator;
		this.termList = termList;
	}
	
	public Term getTerm(){
		return term;
	}
	
	@Override
	public void accept(Visitor visitor) throws SemanticException {	
		visitor.visit(this);	
		
		if (sign != null) {
			sign.accept(visitor);
		}
		
		term.accept(visitor);
		
		for (int i = 0; i < additionalOperator.size(); i++) {
			additionalOperator.get(i).accept(visitor);
			termList.get(i).accept(visitor);
		}
	}
}
