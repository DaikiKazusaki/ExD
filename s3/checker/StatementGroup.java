package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class StatementGroup {
	private List<Statement> statement = new ArrayList<>();

	public StatementGroup(List<Statement> statement) {
		this.statement = statement;
	}
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
		for(Statement item: statement) {
			item.accept(visitor);
		}
	}
}
