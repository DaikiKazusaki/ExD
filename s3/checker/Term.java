package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class Term {
	Factor nesessaryFactor;
	List<MultipleOperator> multipleOperator = new ArrayList<>();
	List<Factor> optionalFactor = new ArrayList<>();
	
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
