package enshud.s3.checker;

public class LeftSide implements Node {
    private Variable variable;

    public LeftSide(Variable variable) {
        this.variable = variable;
    }

    public Variable getVariable() {
        return variable;
    }

    @Override
    public void accept(Visitor visitor) throws SemanticException {
        visitor.visit(this);
    }
}
