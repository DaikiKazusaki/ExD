package enshud.s3.checker;

public class RelationalOperator implements Node {
    private String relationalOperator;

    public RelationalOperator(String relationalOperator) {
        this.relationalOperator = relationalOperator;
    }

    public String getRelationalOperator() {
        return relationalOperator;
    }

    @Override
    public void accept(Visitor visitor) throws SemanticException {
        visitor.visit(this);
    }
}
