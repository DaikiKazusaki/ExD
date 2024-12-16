package enshud.s3.checker;

public class FormalParameter implements Element {
	private FormalParameterGroup formalParameterGroup;

	public FormalParameter(FormalParameterGroup formalParameterGroup) {
		this.formalParameterGroup = formalParameterGroup;
	}
	
	@Override
	public void accept(Visitor visitor) throws SemanticException {
		visitor.visit(this);
		if (formalParameterGroup != null) {
			formalParameterGroup.accept(visitor);
		}
	}
}
