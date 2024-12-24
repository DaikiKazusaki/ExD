package enshud.s4.compiler;

public class Sign implements Node {
    private String sign;

    public Sign(String sign) {
        this.sign = sign;
    }

    public String getSign() {
        return sign;
    }

    @Override
    public void accept(Visitor visitor) throws SemanticException {
        visitor.visit(this);
    }
}
