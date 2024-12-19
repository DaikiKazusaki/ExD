package enshud.s4.compiler;

public class StandardType implements Element {
    private String standardType;

    public StandardType(String standardType) {
        this.standardType = standardType;
    }

    public String getStandardType() {
        return standardType;
    }

    @Override
    public void accept(Visitor visitor) throws SemanticException {
        visitor.visit(this);
    }
}
