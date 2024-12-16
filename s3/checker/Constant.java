package enshud.s3.checker;

public class Constant implements Element {
	private String token;
	private UnsignedInteger unsignedInteger;
	
	public Constant(String token, UnsignedInteger unsignedInteger) {
		this.token = token;
		this.unsignedInteger = unsignedInteger;
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
		if (unsignedInteger != null) {
			unsignedInteger.accept(visitor);
		}
	}
	
	public String getToken() {
		return token;
	}
	
	public UnsignedInteger getUnsignedInteger() {
		return unsignedInteger;
	}
}
