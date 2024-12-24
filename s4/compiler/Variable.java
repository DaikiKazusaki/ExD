package enshud.s4.compiler;

public class Variable implements Node {
    private NaturalVariable naturalVariable;
    private VariableWithIndex variableWithIndex;

    public Variable(NaturalVariable naturalVariable, VariableWithIndex variableWithIndex) {
        this.naturalVariable = naturalVariable;
        this.variableWithIndex = variableWithIndex;
    }

    public NaturalVariable getNaturalVariable() {
        return naturalVariable;
    }

    public VariableWithIndex getVariableWithIndex() {
        return variableWithIndex;
    }

    @Override
    public void accept(Visitor visitor) throws SemanticException {
        visitor.visit(this);
    }
}
