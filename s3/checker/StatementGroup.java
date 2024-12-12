package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class StatementGroup implements Element {
	private List<Statement> statement = new ArrayList<>();
	
	public StatementGroup(List<Statement> statement) {
		this.statement = statement;
	}

	@Override
	public void accept(Visitor visitor) throws SemanticException {
		visitor.visit(this);
		for (Statement item: statement) {
			item.accept(visitor);
		}
	}
}
