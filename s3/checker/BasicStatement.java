package enshud.s3.checker;

public class BasicStatement {
	private AssignStatement assignStatement;
	private ProcedureCallStatement procedureCallStatement;
	private InputOutputStatement inputOutputStatement;
	private ComplexStatement complexStatement;
	
	public BasicStatement(AssignStatement assignStatement, ProcedureCallStatement procedureCallStatement, InputOutputStatement inputOutputStatement, ComplexStatement complexStatement) {
		this.assignStatement = assignStatement;
		this.procedureCallStatement = procedureCallStatement;
		this.inputOutputStatement = inputOutputStatement;
		this.complexStatement = complexStatement;
	}
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
