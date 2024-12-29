package enshud.s4.compiler;

import java.util.List;

public class Equation implements Node {
    private List<SimpleEquation> simpleEquationList;
    private RelationalOperator relationalOperator;
    private String lineNum;

    public Equation(List<SimpleEquation> simpleEquationList, RelationalOperator relationalOperator, String lineNum) {
        this.simpleEquationList = simpleEquationList;
        this.relationalOperator = relationalOperator;
        this.lineNum = lineNum;
    }

	public List<SimpleEquation> getSimpleEquationList() {
        return simpleEquationList;
    }

    public RelationalOperator getRelationalOperator() {
        return relationalOperator;
    }

    @Override
    public void accept(Visitor visitor) throws SemanticException {
        visitor.visit(this);
    }

	public String getLineNum() {
		return lineNum;
	}
}
