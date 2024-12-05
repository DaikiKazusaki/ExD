package enshud.s3.checker;

public class VariableName implements Element {
    private String variableName; 

    public VariableName(String variableName) {
        this.variableName = variableName;
    }

    public String getVariableName() {
        return variableName;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
