package enshud.s3.checker;

public class Integer implements Element {
	private Sign sign;
	private UnsignedInteger unsignedInteger;
	
	public Integer(Sign sign, UnsignedInteger unsignedInteger) {
		this.sign = sign;
		this.unsignedInteger = unsignedInteger;
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
		if (sign != null) {
			sign.accept(visitor);
		}
		unsignedInteger.accept(visitor);
	}
}
