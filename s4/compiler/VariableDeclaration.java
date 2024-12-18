package enshud.s4.compiler;

public class VariableDeclaration implements Element {
    private VariableDeclarationGroup variableDeclarationGroup;

    public VariableDeclaration(VariableDeclarationGroup variableDeclarationGroup) {
        this.variableDeclarationGroup = variableDeclarationGroup;
    }

    public VariableDeclarationGroup getVariableDeclarationGroup() {
        return variableDeclarationGroup;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
