package enshud.s4.compiler;

import java.util.List;

public class Equation implements Node {
    private List<SimpleEquation> simpleEquationList;
    private List<RelationalOperator> relationalOperatorList;

    public Equation(List<SimpleEquation> simpleEquationList, List<RelationalOperator> relationalOperatorList) {
        this.simpleEquationList = simpleEquationList;
        this.relationalOperatorList = relationalOperatorList;
    }

    public List<SimpleEquation> getSimpleEquationList() {
        return simpleEquationList;
    }

    public List<RelationalOperator> getRelationalOperatorList() {
        return relationalOperatorList;
    }

    @Override
    public void accept(Visitor visitor) throws SemanticException {
        visitor.visit(this);
    }
}
