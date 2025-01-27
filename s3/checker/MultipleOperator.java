package enshud.s3.checker;

public class MultipleOperator implements Node {
    private String multipleOperator;

    public MultipleOperator(String multipleOperator) {
        this.multipleOperator = multipleOperator;
    }

    public String getMultipleOperator() {
        return multipleOperator;
    }

    @Override
    public void accept(Visitor visitor) throws SemanticException {
        visitor.visit(this);
    }
}
