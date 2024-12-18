package enshud.s4.compiler;

public class Constant implements Element {
    private String constant;

    public Constant(String constant) {
        this.constant = constant;
    }

    public String getConstant() {
        return constant;
    }

    @Override
    public void accept(Visitor visitor) throws SemanticException {
        visitor.visit(this);
    }
}
