package enshud.s4.compiler;

import java.util.List;

public class SubprogramDeclarationGroup implements Element {
    private List<SubprogramDeclaration> subprogramDeclaration;

    public SubprogramDeclarationGroup(List<SubprogramDeclaration> subprogramDeclarationList) {
        this.subprogramDeclaration = subprogramDeclarationList;
    }

    public List<SubprogramDeclaration> getSubprogramDeclaration() {
        return subprogramDeclaration;
    }

    @Override
    public void accept(Visitor visitor) throws SemanticException {
        visitor.visit(this);
    }
}
