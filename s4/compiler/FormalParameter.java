package enshud.s4.compiler;

public class FormalParameter implements Node {
    private FormalParameterGroup formalParameterGroup;

    public FormalParameter(FormalParameterGroup formalParameterGroup) {
        this.formalParameterGroup = formalParameterGroup;
    }

    public FormalParameterGroup getFormalParameterGroup() {
        return formalParameterGroup;
    }

    @Override
    public void accept(Visitor visitor) throws SemanticException {
        visitor.visit(this);
    }
}
