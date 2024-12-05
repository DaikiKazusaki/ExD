package enshud.s3.checker;

public class Integer {
	private Sign sign;
	private UnsignedInteger unsignedInteger;
	
	public Integer(Sign sign, UnsignedInteger unsignedInteger) {
		this.sign = sign;
		this.unsignedInteger = unsignedInteger;
	}
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
		sign.accept(visitor);
		unsignedInteger.accept(visitor);
	}
}
