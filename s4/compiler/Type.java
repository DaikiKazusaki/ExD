package enshud.s4.compiler;

public class Type implements Node {
    private StandardType standardType;
    private ArrayType arrayType;

    public Type(StandardType standardType, ArrayType arrayType) {
        this.standardType = standardType;
        this.arrayType = arrayType;
    }

    public StandardType getStandardType() {
        return standardType;
    }

    public ArrayType getArrayType() {
        return arrayType;
    }

    @Override
    public void accept(Visitor visitor) throws SemanticException {
        visitor.visit(this);
    }
}
