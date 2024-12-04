package enshud.s3.checker;

public class BasicStatement {
	AssignStatement assignStatement;
	ProcedureCallStatement procedureCallStatement;
	InputOutputStatement inputOutputStatement;
	ComplexStatement complexStatement;
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
		
		if (assignStatement != null) {
			assignStatement.accept(visitor);
		} else if (procedureCallStatement != null) {
			procedureCallStatement.accept(visitor);
		} else if (inputOutputStatement != null) {
			inputOutputStatement.accept(visitor);
		} else {
			complexStatement.accept(visitor);
		}
	}
}
