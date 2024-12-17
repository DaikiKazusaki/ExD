package enshud.s4.compiler;

public class ArrayType implements Element {
	private Int minimumInteger;
	private Int maximumInteger;
	private StandardType standardtype;
	
	public ArrayType(Int minimumInteger, Int maximumInteger, StandardType standardType) {
		this.minimumInteger = minimumInteger;
		this.maximumInteger = maximumInteger;
		this.standardtype = standardType;
	}

	@Override
	public void accept(Visitor visitor) throws SemanticException {
		visitor.visit(this);
	}

}
