package enshud.s4.compiler;

public class WhileDo implements Node {
    private Equation equation;
    private ComplexStatement complexStatement;

    public WhileDo(Equation equation, ComplexStatement complexStatement) {
        this.equation = equation;
        this.complexStatement = complexStatement;
    }

    public Equation getEquation() {
        return equation;
    }

    public ComplexStatement getComplexStatement() {
        return complexStatement;
    }

    @Override
    public void accept(Visitor visitor) throws SemanticException {
        visitor.visit(this);
    }
}
