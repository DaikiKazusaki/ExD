package enshud.s3.checker;

public class Constant {
	private String token;
	private UnsignedInteger unsignedInteger;
	
	public Constant(String token, UnsignedInteger unsignedInteger) {
		this.token = token;
		this.unsignedInteger = unsignedInteger;
	}
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
		unsignedInteger.accept(visitor);
	}
	
	public String getToken() {
		return token;
	}
}
