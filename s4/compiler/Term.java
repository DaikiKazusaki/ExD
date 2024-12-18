package enshud.s4.compiler;

import java.util.List;

public class Term implements Element {
    private List<Factor> factorList;
    private List<MultipleOperator> multipleOperatorList;

    public Term(List<Factor> factorList, List<MultipleOperator> multipleOperatorList) {
        this.factorList = factorList;
        this.multipleOperatorList = multipleOperatorList;
    }

    public List<Factor> getFactorList() {
        return factorList;
    }

    public List<MultipleOperator> getMultipleOperatorList() {
        return multipleOperatorList;
    }

    @Override
    public void accept(Visitor visitor) throws SemanticException {
        visitor.visit(this);
    }
}
