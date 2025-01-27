package enshud.s3.checker;

public class UnsignedInteger implements Node {
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
