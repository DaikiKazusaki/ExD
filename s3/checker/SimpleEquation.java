package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class SimpleEquation {
	private Sign sign;
	private Term nesessaryTerm;
	private AdditionalOperator additionalOperator;
	private Term optionalTerm;
	
	public SimpleEquation(Sign sign, Term nesessaryTerm, AdditionalOperator additionalOperator, Term optionalTerm) {
		this.sign = sign;
		this.nesessaryTerm = nesessaryTerm;
		this.additionalOperator = additionalOperator;
		this.optionalTerm = optionalTerm;
	}
	
	public void accept(Visitor visitor) {	
		visitor.visit(this);	
	}
}
