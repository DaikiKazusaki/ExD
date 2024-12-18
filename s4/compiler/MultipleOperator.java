package enshud.s4.compiler;

public class MultipleOperator implements Element {
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
