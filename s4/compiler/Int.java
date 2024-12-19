package enshud.s4.compiler;

public class Int implements Element {
    private Sign sign;
    private UnsignedInteger unsignedInteger;

    public Int(Sign sign, UnsignedInteger unsignedInteger) {
        this.sign = sign;
        this.unsignedInteger = unsignedInteger;
    }

    public Sign getSign() {
        return sign;
    }

    public UnsignedInteger getUnsignedInteger() {
        return unsignedInteger;
    }

    @Override
    public void accept(Visitor visitor) throws SemanticException {
        visitor.visit(this);
    }
}
