package enshud.s3.checker;

import java.util.List;

public class StatementGroup implements Node {
    private List<Statement> statementList;

    public StatementGroup(List<Statement> statementList) {
        this.statementList = statementList;
    }

    public List<Statement> getStatementList() {
        return statementList;
    }

    @Override
    public void accept(Visitor visitor) throws SemanticException {
        visitor.visit(this);
    }
}
