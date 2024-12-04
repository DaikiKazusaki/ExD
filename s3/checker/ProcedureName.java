package enshud.s3.checker;

public class ProcedureName {
	String procedureName;

	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
