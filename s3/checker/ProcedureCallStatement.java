package enshud.s3.checker;

public class ProcedureCallStatement {
	private String procedureName;
	private EquationGroup equationGroup;
	
	public ProcedureCallStatement(String procedureName, EquationGroup equationGroup) {
		this.procedureName = procedureName;
		this.equationGroup = equationGroup;
	}
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
		equationGroup.accept(visitor);
	}
	
	public String getProcedureName() {
		return procedureName;
	}
}
