package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class StatementGroup {
	private Statement statement;

	public StatementGroup(Statement statement) {
		this.statement = statement;
	}
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
