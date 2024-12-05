package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class Term {
	private Factor nesessaryFactor;
	private List<MultipleOperator> multipleOperator = new ArrayList<>();
	private List<Factor> optionalFactor = new ArrayList<>();
	
	public Term(Factor nesessaryFactor, List<MultipleOperator> multipleOperator, List<Factor> optionalFactor) {
		this.nesessaryFactor = nesessaryFactor;
		this.multipleOperator = multipleOperator;
		this.optionalFactor = optionalFactor;
	}
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
		
		nesessaryFactor.accept(visitor);
		if (multipleOperator != null) {
			for (int i = 0; i < multipleOperator.size(); i++) {
				multipleOperator.get(i).accept(visitor);
				optionalFactor.get(i).accept(visitor);
			}
		}
	}
}
