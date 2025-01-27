package enshud.s3.checker;

public class ComplexStatement implements Node {
    private StatementGroup statementGroup;

    public ComplexStatement(StatementGroup statementGroup) {
        this.statementGroup = statementGroup;
    }

    public StatementGroup getStatementGroup() {
        return statementGroup;
    }

    @Override
    public void accept(Visitor visitor) throws SemanticException {
        visitor.visit(this);
    }
}
