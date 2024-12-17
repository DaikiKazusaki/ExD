package enshud.s4.compiler;

public class SubprogramHead implements Element {
	private ProcedureName procedureName;
	private FormalParameter formalParameter;

	public SubprogramHead(ProcedureName procedureName, FormalParameter formalParameter) {
		this.procedureName = procedureName;
		this.formalParameter = formalParameter;
	}

	@Override
	public void accept(Visitor visitor) throws SemanticException {
		visitor.visit(this);
	}

}
