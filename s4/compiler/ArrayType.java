package enshud.s4.compiler;

public class ArrayType implements Element {
	private Int minimumInteger;
	private Int maximumInteger;
	private StandardType standardType;
	
	public ArrayType(Int minimumInteger, Int maximumInteger, StandardType standardType) {
		this.minimumInteger = minimumInteger;
		this.maximumInteger = maximumInteger;
		this.standardType = standardType;
	}

	public Int getMinimumInteger() {
        return minimumInteger;
    }

    public Int getMaximumInteger() {
        return maximumInteger;
    }

    public StandardType getStandardType() {
        return standardType;
    }
    
	@Override
	public void accept(Visitor visitor) throws SemanticException {
		visitor.visit(this);
	}

}
