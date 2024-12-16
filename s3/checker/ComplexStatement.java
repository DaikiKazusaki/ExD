package enshud.s3.checker;

public class ComplexStatement implements Element {
	private StatementGroup statementGroup;
	
	public ComplexStatement(StatementGroup statementGroup) {
		this.statementGroup = statementGroup;
	}

	@Override
	public void accept(Visitor visitor) throws SemanticException {
		visitor.visit(this);
		statementGroup.accept(visitor);
	}
}
