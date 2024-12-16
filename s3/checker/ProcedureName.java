package enshud.s3.checker;

public class ProcedureName implements Element {
	private String procedureName;

	public ProcedureName(String procedureName) {
		this.procedureName = procedureName;
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
	
	public String getProcedureName() {
		return procedureName;
	}
}
