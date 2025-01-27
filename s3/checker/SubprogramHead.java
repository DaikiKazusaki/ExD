package enshud.s3.checker;

public class SubprogramHead implements Node {
    private ProcedureName procedureName;
    private FormalParameter formalParameter;
    private String lineNum;

    public SubprogramHead(ProcedureName procedureName, FormalParameter formalParameter, String lineNum) {
        this.procedureName = procedureName;
        this.formalParameter = formalParameter;
        this.lineNum = lineNum;
    }

    public ProcedureName getProcedureName() {
        return procedureName;
    }

    public FormalParameter getFormalParameter() {
        return formalParameter;
    }
    
    public String getLineNum() {
    	return lineNum;
    }

    @Override
    public void accept(Visitor visitor) throws SemanticException {
        visitor.visit(this);
    }
}
