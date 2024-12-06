package enshud.s3.checker;

public class UnsignedInteger {
	private String number;

	public UnsignedInteger(String number) {
		this.number = number;
	}
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
	
	public String getNumber() {
		return number;
	}
}
