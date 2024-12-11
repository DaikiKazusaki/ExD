package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class Term implements Element {
	private Factor nesessaryFactor;
	private List<MultipleOperator> multipleOperator = new ArrayList<>();
	private List<Factor> optionalFactor = new ArrayList<>();
	
	public Term(Factor nesessaryFactor, List<MultipleOperator> multipleOperator, List<Factor> factor) {
		this.nesessaryFactor = nesessaryFactor;
		this.multipleOperator = multipleOperator;
		this.optionalFactor = factor;
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
		nesessaryFactor.accept(visitor);
		for (int i = 0; i < multipleOperator.size(); i++) {
			multipleOperator.get(i).accept(visitor);
			optionalFactor.get(i).accept(visitor);
		}
	}
}
