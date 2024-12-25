package enshud.s4.compiler;

public class ProcedureCallStatement implements Node {
    private ProcedureName procedureName;
    private EquationGroup equationGroup;
    private String lineNum;

    public ProcedureCallStatement(ProcedureName procedureName, EquationGroup equationGroup, String lineNum) {
        this.procedureName = procedureName;
        this.equationGroup = equationGroup;
        this.lineNum = lineNum;
    }

    public ProcedureName getProcedureName() {
        return procedureName;
    }

    public EquationGroup getEquationGroup() {
        return equationGroup;
    }
    
    public String getLineNum() {
    	return lineNum;
    }

    @Override
    public void accept(Visitor visitor) throws SemanticException {
        visitor.visit(this);
    }
}
