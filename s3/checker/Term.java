package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class Term implements Element {
	private Factor factor;
	private List<MultipleOperator> multipleOperator = new ArrayList<>();
	private List<Factor> factorList = new ArrayList<>();
	
	public Term(Factor factor, List<MultipleOperator> multipleOperator, List<Factor> factorList) {
		this.factor = factor;
		this.multipleOperator = multipleOperator;
		this.factorList = factorList;
	}
	
	public Factor getFactor(){
		return factor;
	}
	
	@Override
	public void accept(Visitor visitor) throws SemanticException {
		visitor.visit(this);
		factor.accept(visitor);
		for (int i = 0; i < multipleOperator.size(); i++) {
			multipleOperator.get(i).accept(visitor);
			factorList.get(i).accept(visitor);
		}
	}
}
