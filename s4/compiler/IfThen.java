package enshud.s4.compiler;

public class IfThen implements Node {
    private Equation equation;
    private ComplexStatement complexStatement;
    private ElseStatement elseStatement;

    public IfThen(Equation equation, ComplexStatement complexStatement, ElseStatement elseStatement) {
        this.equation = equation;
        this.complexStatement = complexStatement;
        this.elseStatement = elseStatement;
    }

    public Equation getEquation() {
        return equation;
    }

    public ComplexStatement getComplexStatement() {
        return complexStatement;
    }

    public ElseStatement getElseStatement() {
        return elseStatement;
    }

    @Override
    public void accept(Visitor visitor) throws SemanticException {
        visitor.visit(this);
    }
}
