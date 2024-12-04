package enshud.s3.checker;

public class ProcedureCallStatement {
	String procedureName;
	EquationGroup equationGroup;
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
		
		equationGroup.accept(visitor);
	}
}
