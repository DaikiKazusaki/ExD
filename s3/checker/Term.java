package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class Term {
	private Factor nesessaryFactor;
	private List<MultipleOperator> multipleOperator = new ArrayList<>();
	private List<Factor> optionalFactor = new ArrayList<>();
	
	public Term(Factor nesessaryFactor, List<MultipleOperator> multipleOperator, List<Factor> factor) {
		this.nesessaryFactor = nesessaryFactor;
		this.multipleOperator = multipleOperator;
		this.optionalFactor = factor;
	}
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
