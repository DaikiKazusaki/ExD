package enshud.s4.compiler;

public class NaturalVariable implements Element {
    private VariableName variableName;

    public NaturalVariable(VariableName variableName) {
        this.variableName = variableName;
    }

    public VariableName getVariableName() {
        return variableName;
    }

    @Override
    public void accept(Visitor visitor) throws SemanticException {
        visitor.visit(this);
    }
}
