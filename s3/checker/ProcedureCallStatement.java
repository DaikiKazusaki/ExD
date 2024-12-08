package enshud.s3.checker;

public class ProcedureCallStatement implements Element {
	private ProcedureName procedureName;
	private EquationGroup equationGroup;
	
	public ProcedureCallStatement(ProcedureName procedureName, EquationGroup equationGroup) {
		this.procedureName = procedureName;
		this.equationGroup = equationGroup;
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
		procedureName.accept(visitor);
		equationGroup.accept(visitor);
	}
}
