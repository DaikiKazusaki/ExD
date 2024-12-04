package enshud.s3.checker;

public class ComplexStatement {
	StatementGroup statementGroup;

	public void accept(Visitor visitor) {
		visitor.visit(this);
		statementGroup.accept(visitor);
	}
}
