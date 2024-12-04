package enshud.s3.checker;

public class ComplexStatement {
	private StatementGroup statementGroup;

	public ComplexStatement(StatementGroup statementGroup) {
		this.statementGroup = statementGroup;
	}
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
		statementGroup.accept(visitor);
	}
}
