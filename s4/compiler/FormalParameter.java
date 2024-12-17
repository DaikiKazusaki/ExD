package enshud.s4.compiler;

public class FormalParameter implements Element {
	private FormalParameterGroup formalParameterGroup;

	public FormalParameter(FormalParameterGroup formalParameterGroup) {
		this.formalParameterGroup = formalParameterGroup;
	}

	@Override
	public void accept(Visitor visitor) throws SemanticException {
		visitor.visit(this);
	}

}
