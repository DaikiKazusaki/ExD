package enshud.s4.compiler;

import java.util.List;

public class Term implements Node {
    private List<Factor> factorList;
    private List<MultipleOperator> multipleOperatorList;
    private String lineNum;

    public Term(List<Factor> factorList, List<MultipleOperator> multipleOperatorList, String lineNum) {
        this.factorList = factorList;
        this.multipleOperatorList = multipleOperatorList;
        this.lineNum = lineNum;
    }

    public List<Factor> getFactorList() {
        return factorList;
    }

    public List<MultipleOperator> getMultipleOperatorList() {
        return multipleOperatorList;
    }
    
    public String getLineNum() {
    	return lineNum;
    }

    @Override
    public void accept(Visitor visitor) throws SemanticException {
        visitor.visit(this);
    }
}
