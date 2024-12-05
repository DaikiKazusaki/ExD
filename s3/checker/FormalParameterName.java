package enshud.s3.checker;

public class FormalParameterName {
	private String formalParameterName;

	public FormalParameterName(String formalParameterName) {
		this.formalParameterName = formalParameterName;
	}
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
	
	public String getFormalParameterName() {
		return formalParameterName;
	}
}
