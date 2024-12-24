package enshud.s4.compiler;

public class Int implements Node {
    private Sign sign;
    private UnsignedInteger unsignedInteger;
    private String lineNum;

    public Int(Sign sign, UnsignedInteger unsignedInteger, String lineNum) {
        this.sign = sign;
        this.unsignedInteger = unsignedInteger;
        this.lineNum = lineNum;
    }

    public Sign getSign() {
        return sign;
    }

    public UnsignedInteger getUnsignedInteger() {
        return unsignedInteger;
    }

    public String getLineNum() {
    	return lineNum;
    }
    
    @Override
    public void accept(Visitor visitor) throws SemanticException {
        visitor.visit(this);
    }
}
