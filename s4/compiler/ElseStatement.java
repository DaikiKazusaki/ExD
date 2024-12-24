package enshud.s4.compiler;

public class ElseStatement implements Node {
    private ComplexStatement complexStatement;

    public ElseStatement(ComplexStatement complexStatement) {
        this.complexStatement = complexStatement;
    }

    public ComplexStatement getComplexStatement() {
        return complexStatement;
    }

    @Override
    public void accept(Visitor visitor) throws SemanticException {
        visitor.visit(this);
    }
}
