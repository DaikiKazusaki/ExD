package enshud.s3.checker;

public class LeftSide {
	private String variableName;

	public LeftSide(String variableName) {
		this.variableName = variableName;
	}
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
	
	public String getVariableName() {
		return variableName;
	}
}
