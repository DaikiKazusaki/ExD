package enshud.s3.checker;

public class Term {
	Factor nesessaryFactor;
	MultipleOperator multipleOperator;
	Factor optionalFactor;
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
		
		nesessaryFactor.accept(visitor);
		if (multipleOperator != null) {
			multipleOperator.accept(visitor);
			optionalFactor.accept(visitor);
		}
	}
}
