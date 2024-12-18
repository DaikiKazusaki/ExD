package enshud.s4.compiler;

public class LeftSide implements Element {
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
