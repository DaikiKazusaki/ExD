package enshud.s3.checker;

public class FormalParameter {
	private FormalParameterGroup formalParameterGroup;

	public FormalParameter(FormalParameterGroup formalParameterGroup) {
		this.formalParameterGroup = formalParameterGroup;
	}
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
		
		if (formalParameterGroup != null) {
			formalParameterGroup.accept(visitor);
		}
	}
}
