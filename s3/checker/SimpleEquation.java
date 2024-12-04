package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class SimpleEquation {
	Sign sign;
	Term nesessaryTerm;
	List<AdditionalOperator> additionalOperator = new ArrayList<>();
	List<Term> optionalTerm = new ArrayList<>();
	
	public void accept(Visitor visitor) {	
		visitor.visit(this);
		
		if (sign != null) {
			sign.accept(visitor);
		}
		nesessaryTerm.accept(visitor);
		if (additionalOperator != null) {
			for (int i = 0; i < additionalOperator.size(); i++) {
				additionalOperator.get(i).accept(visitor);
				optionalTerm.get(i).accept(visitor);
			}
		}		
	}
}
