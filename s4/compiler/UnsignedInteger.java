package enshud.s4.compiler;

public class UnsignedInteger implements Element {
    private String unsignedInteger;

    public UnsignedInteger(String unsignedInteger) {
        this.unsignedInteger = unsignedInteger;
    }

    public String getUnsignedInteger() {
        return unsignedInteger;
    }

    @Override
    public void accept(Visitor visitor) throws SemanticException {
        visitor.visit(this);
    }
}
