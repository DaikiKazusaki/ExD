package enshud.s3.checker;

public class InputOutputStatement implements Node {
    private VariableGroup variableGroup;
    private EquationGroup equationGroup;

    public InputOutputStatement(VariableGroup variableGroup, EquationGroup equationGroup) {
        this.variableGroup = variableGroup;
        this.equationGroup = equationGroup;
    }

    public VariableGroup getVariableGroup() {
        return variableGroup;
    }

    public EquationGroup getEquationGroup() {
        return equationGroup;
    }

    @Override
    public void accept(Visitor visitor) throws SemanticException {
        visitor.visit(this);
    }
}
