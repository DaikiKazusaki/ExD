package enshud.s4.compiler;

public class ProcedureName implements Element {
	private String procedureName;

	public ProcedureName(String procedureName) {
		this.procedureName = procedureName;
	}

	@Override
	public void accept(Visitor visitor) throws SemanticException {
		visitor.visit(this);
	}

}
