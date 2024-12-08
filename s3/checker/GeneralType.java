package enshud.s3.checker;

public class GeneralType implements Element {
	private String type;
	
	public GeneralType(String type) {
		this.type = type;
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
	
	public String getGeneralType() {
		return type;
	}
}
