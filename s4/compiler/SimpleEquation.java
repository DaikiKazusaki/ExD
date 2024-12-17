package enshud.s4.compiler;

import java.util.List;

public class SimpleEquation implements Element {
	private Sign sign;
	private List<Term> termList;
	private List<AdditionalOperator> additionalOperatorList;

	public SimpleEquation(Sign sign, List<Term> termList, List<AdditionalOperator> additionalOperatorList) {
		this.sign = sign;
		this.termList = termList;
		this.additionalOperatorList = additionalOperatorList;
	}

	@Override
	public void accept(Visitor visitor) throws SemanticException {
		visitor.visit(this);
	}

}
