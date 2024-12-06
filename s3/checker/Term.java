package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class Term {
	private Factor nesessaryFactor;
	private MultipleOperator multipleOperator;
	private Factor optionalFactor;
	
	public Term(Factor nesessaryFactor, MultipleOperator multipleOperator, Factor optionalFactor) {
		this.nesessaryFactor = nesessaryFactor;
		this.multipleOperator = multipleOperator;
		this.optionalFactor = optionalFactor;
	}
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
