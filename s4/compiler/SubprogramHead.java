package enshud.s4.compiler;

public class SubprogramHead implements Node {
    private ProcedureName procedureName;
    private FormalParameter formalParameter;

    public SubprogramHead(ProcedureName procedureName, FormalParameter formalParameter) {
        this.procedureName = procedureName;
        this.formalParameter = formalParameter;
    }

    public ProcedureName getProcedureName() {
        return procedureName;
    }

    public FormalParameter getFormalParameter() {
        return formalParameter;
    }

    @Override
    public void accept(Visitor visitor) throws SemanticException {
        visitor.visit(this);
    }
}
