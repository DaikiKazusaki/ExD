package enshud.s3.checker;

import java.util.List;

public class SubprogramDeclarationGroup implements Node {
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
