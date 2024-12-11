package enshud.s3.checker;

public class SubprogramHead implements Element {
	private ProcedureName procedureName;
	private FormalParameter formalParameter;
	
	public SubprogramHead(ProcedureName procedureName, FormalParameter formalParameter) {
		this.procedureName = procedureName;
		this.formalParameter = formalParameter;
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
		procedureName.accept(visitor);
		formalParameter.accept(visitor);
	}
}
