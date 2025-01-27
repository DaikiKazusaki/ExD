package enshud.s3.checker;

public class ProcedureName implements Node {
    private String procedureName;

    public ProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    public String getProcedureName() {
        return procedureName;
    }

    @Override
    public void accept(Visitor visitor) throws SemanticException {
        visitor.visit(this);
    }
}
