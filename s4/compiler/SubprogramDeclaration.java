package enshud.s4.compiler;

public class SubprogramDeclaration implements Element {
    private SubprogramHead subprogramHead;
    private VariableDeclaration variableDeclaration;
    private ComplexStatement complexStatement;

    public SubprogramDeclaration(SubprogramHead subprogramHead, VariableDeclaration variableDeclaration, ComplexStatement complexStatement) {
        this.subprogramHead = subprogramHead;
        this.variableDeclaration = variableDeclaration;
        this.complexStatement = complexStatement;
    }

    public SubprogramHead getSubprogramHead() {
        return subprogramHead;
    }

    public VariableDeclaration getVariableDeclaration() {
        return variableDeclaration;
    }

    public ComplexStatement getComplexStatement() {
        return complexStatement;
    }

    @Override
    public void accept(Visitor visitor) throws SemanticException {
        visitor.visit(this);
    }
}
