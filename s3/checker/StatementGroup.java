package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class StatementGroup {
	List<Statement> statement = new ArrayList<>();

	// @Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
		for(Statement item: statement) {
			item.accept(visitor);
		}
	}
}
