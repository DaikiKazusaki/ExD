package enshud.s3.checker;

public class GeneralType {
	private String type;
	
	// @Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
	
	public String getGeneralType() {
		return type;
	}
}
