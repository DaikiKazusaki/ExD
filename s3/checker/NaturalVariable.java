package enshud.s3.checker;

public class NaturalVariable implements Node {
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
