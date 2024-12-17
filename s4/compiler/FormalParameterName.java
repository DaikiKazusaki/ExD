package enshud.s4.compiler;

public class FormalParameterName implements Element {
	private String formalParameterName;
	
	public FormalParameterName(String formalParameterName) {
		this.formalParameterName = formalParameterName;
	}

	@Override
	public void accept(Visitor visitor) throws SemanticException {
		visitor.visit(this);
	}

}
