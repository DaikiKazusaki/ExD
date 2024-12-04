package enshud.s3.checker;

public class FormalParameter {
	FormalParameterGroup formalParameterGroup;

	public void accept(Visitor visitor) {
		visitor.visit(this);
		
		if (formalParameterGroup != null) {
			formalParameterGroup.accept(visitor);
		}
	}
}
