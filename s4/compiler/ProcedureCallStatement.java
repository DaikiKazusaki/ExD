package enshud.s4.compiler;

public class ProcedureCallStatement implements Element {
	private ProcedureName procedureName;
	private EquationGroup equationGroup;

	public ProcedureCallStatement(ProcedureName procedureName, EquationGroup equationGroup) {
		this.procedureName = procedureName;
		this.equationGroup = equationGroup;
	}

	@Override
	public void accept(Visitor visitor) throws SemanticException {
		visitor.visit(this);
	}

}
