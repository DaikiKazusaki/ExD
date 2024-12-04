package enshud.s3.checker;

public class Statement {
	BasicStatement basicStatement;
	IfThenElse ifThenElse;
	IfThen ifThen;
	WhileDo whileDo;
	
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
