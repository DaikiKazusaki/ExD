package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class Term implements Element {
	private List<Factor> factor = new ArrayList<>();
	private List<MultipleOperator> multipleOperator = new ArrayList<>();
	
	public Term(List<Factor> factor, List<MultipleOperator> multipleOperator) {
		this.factor = factor;
		this.multipleOperator = multipleOperator;
	}
	
	public List<Factor> getFactorList(){
		return factor;
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
		factor.get(0).accept(visitor);
		for (int i = 0; i < multipleOperator.size(); i++) {
			multipleOperator.get(i).accept(visitor);
			factor.get(i + 1).accept(visitor);
		}
	}
}
