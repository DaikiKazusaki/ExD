package enshud.s4.compiler;

import java.util.List;

public class SimpleEquation implements Node {
    private Sign sign;
    private List<Term> termList;
    private List<AdditionalOperator> additionalOperatorList;
    private String lineNum;

    public SimpleEquation(Sign sign, List<Term> termList, List<AdditionalOperator> additionalOperatorList, String lineNum) {
        this.sign = sign;
        this.termList = termList;
        this.additionalOperatorList = additionalOperatorList;
        this.lineNum = lineNum;
    }

    public Sign getSign() {
        return sign;
    }

    public List<Term> getTermList() {
        return termList;
    }

    public List<AdditionalOperator> getAdditionalOperatorList() {
        return additionalOperatorList;
    }
    
    public String getLineNum() {
    	return lineNum;
    }

    @Override
    public void accept(Visitor visitor) throws SemanticException {
        visitor.visit(this);
    }
}
