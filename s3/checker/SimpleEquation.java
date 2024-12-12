package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class SimpleEquation implements Element {
	private Sign sign;
	private List<Term> term = new ArrayList<>();
	private List<AdditionalOperator> additionalOperator = new ArrayList<>();
	
	public SimpleEquation(Sign sign, List<Term> term, List<AdditionalOperator> additionalOperator) {
		this.sign = sign;
		this.term = term;
		this.additionalOperator = additionalOperator;
	}
	
	public List<Term> getTermList(){
		return term;
	}
	
	@Override
	public void accept(Visitor visitor) throws SemanticException {	
		visitor.visit(this);	
		
		if (sign != null) {
			sign.accept(visitor);
		}
		
		term.get(0).accept(visitor);
		
		for (int i = 0; i < additionalOperator.size(); i++) {
			additionalOperator.get(i).accept(visitor);
			term.get(i + 1).accept(visitor);
		}
	}
}
