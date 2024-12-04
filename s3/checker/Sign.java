package enshud.s3.checker;

public class Sign {
	private String sign;

	public Sign(String sign) {
		this.sign = sign;
	}
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
	
	public String getSign() {
		return sign;
	}
}
