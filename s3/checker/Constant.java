package enshud.s3.checker;

public class Constant {
	private UnsignedInteger unsignedInteger;
	private CharGroup charGroup;
	
	public Constant(UnsignedInteger unsignedInteger, CharGroup charGroup) {
		this.unsignedInteger = unsignedInteger;
		this.charGroup = charGroup;
	}
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
		
		unsignedInteger.accept(visitor);
		charGroup.accept(visitor);
	}
}
