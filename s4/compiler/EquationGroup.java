package enshud.s4.compiler;

import java.util.List;

public class EquationGroup implements Node {
    private List<Equation> equationList;

    public EquationGroup(List<Equation> equationList) {
        this.equationList = equationList;
    }

    public List<Equation> getEquationList() {
        return equationList;
    }

    @Override
    public void accept(Visitor visitor) throws SemanticException {
        visitor.visit(this);
    }
}
