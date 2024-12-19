package enshud.s4.compiler;

public class Index implements Element {
    private Equation equation;

    public Index(Equation equation) {
        this.equation = equation;
    }

    public Equation getEquation() {
        return equation;
    }

    @Override
    public void accept(Visitor visitor) throws SemanticException {
        visitor.visit(this);
    }
}
