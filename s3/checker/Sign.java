package enshud.s3.checker;

public class Sign implements Element {
	private String sign;

	public Sign(String sign) {
		this.sign = sign;
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
	
	public String getSign() {
		return sign;
	}
}
