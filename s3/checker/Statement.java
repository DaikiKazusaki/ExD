package enshud.s3.checker;

public class Statement {
	private BasicStatement basicStatement;
	private Else ifThenElse;
	private IfThen ifThen;
	private WhileDo whileDo;
	
	public Statement(BasicStatement basicStatement, Else ifThenElse, IfThen ifThen, WhileDo whileDo) {
		this.basicStatement = basicStatement;
		this.ifThenElse = ifThenElse;
		this.ifThen = ifThen;
		this.whileDo = whileDo;
	}
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
		
		if (basicStatement != null) {
			basicStatement.accept(visitor);
		} else if (ifThenElse != null) {
			ifThenElse.accept(visitor);
		} else if (ifThen != null) {
			ifThen.accept(visitor);
		} else {
			whileDo.accept(visitor);
		}
	}
}
