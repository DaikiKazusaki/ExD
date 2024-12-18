package enshud.s4.compiler;

public class Block implements Element {
    private VariableDeclaration variableDeclaration;
    private SubprogramDeclarationGroup subprogramDeclarationGroup;

    public Block(VariableDeclaration variableDeclaration, SubprogramDeclarationGroup subprogramDeclarationGroup) {
        this.variableDeclaration = variableDeclaration;
        this.subprogramDeclarationGroup = subprogramDeclarationGroup;
    }

    public VariableDeclaration getVariableDeclaration() {
        return variableDeclaration;
    }

    public SubprogramDeclarationGroup getSubprogramDeclarationGroup() {
        return subprogramDeclarationGroup;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
