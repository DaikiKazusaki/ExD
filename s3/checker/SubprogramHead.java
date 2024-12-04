package enshud.s3.checker;

public class SubprogramHead {
	ProcedureName procedureName;
	FormalParameter formalParameter;
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
		procedureName.accept(visitor);
		formalParameter.accept(visitor);
	}
}
