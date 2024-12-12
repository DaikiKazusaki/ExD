package enshud.s3.checker;

public class ProcedureCallStatement implements Element {
	private ProcedureName procedureName;
	private EquationGroup equationGroup;
	private String lineNum;
	
	public ProcedureCallStatement(ProcedureName procedureName, EquationGroup equationGroup, String lineNum) {
		this.procedureName = procedureName;
		this.equationGroup = equationGroup;
		this.lineNum = lineNum;
	}
	
	public String getLineNum() {
		return lineNum;
	}
	
	@Override
	public void accept(Visitor visitor) throws SemanticException {
		visitor.visit(this);
		procedureName.accept(visitor);
		
		if (equationGroup != null) {
			equationGroup.accept(visitor);
		}
	}
	
	public ProcedureName getProcedureName() {
		return procedureName;
	}
}
