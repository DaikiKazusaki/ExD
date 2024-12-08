package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class StatementGroup implements Element {
	private Statement statement1;
	private List<Statement> statement2 = new ArrayList<>();
	
	public StatementGroup(Statement statement1, List<Statement> statement2) {
		this.statement1 = statement1;
		this.statement2 = statement2;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
		statement1.accept(visitor);
		for (Statement item: statement2) {
			item.accept(visitor);
		}
	}
}
