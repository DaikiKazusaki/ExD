package enshud.s3.checker;

public class FormalParameterName implements Node {
    private String formalParameterName;

    public FormalParameterName(String formalParameterName) {
        this.formalParameterName = formalParameterName;
    }

    public String getFormalParameterName() {
        return formalParameterName;
    }

    @Override
    public void accept(Visitor visitor) throws SemanticException {
        visitor.visit(this);
    }
}
