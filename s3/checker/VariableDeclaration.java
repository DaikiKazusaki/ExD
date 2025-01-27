package enshud.s3.checker;

public class VariableDeclaration implements Node {
    private VariableDeclarationGroup variableDeclarationGroup;

    public VariableDeclaration(VariableDeclarationGroup variableDeclarationGroup) {
        this.variableDeclarationGroup = variableDeclarationGroup;
    }

    public VariableDeclarationGroup getVariableDeclarationGroup() {
        return variableDeclarationGroup;
    }

    @Override
    public void accept(Visitor visitor) throws SemanticException {
        visitor.visit(this);
    }
}
