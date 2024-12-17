package enshud.s4.compiler;

import java.util.List;

public class StatementGroup implements Element {
	private List<Statement> statementList;

	public StatementGroup(List<Statement> statementList) {
		this.statementList = statementList;
	}

	@Override
	public void accept(Visitor visitor) throws SemanticException {
		visitor.visit(this);
	}

}
