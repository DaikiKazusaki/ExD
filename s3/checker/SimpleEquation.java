package enshud.s3.checker;

public class SimpleEquation {
	Sign sign;
	Term nesessaryTerm;
	AdditionalOperator additionalOperator;
	Term optionalTerm;
	
	public void accept(Visitor visitor) {	
		visitor.visit(this);
		
		if (sign != null) {
			sign.accept(visitor);
		}
		nesessaryTerm.accept(visitor);
		if (additionalOperator != null) {
			additionalOperator.accept(visitor);
			optionalTerm.accept(visitor);
		}		
	}
}
