package enshud.s4.compiler;

public class Statement implements Element {
	private BasicStatement basicStatement;
	private IfThen ifThen;
	private WhileDo whileDo;

	public Statement(BasicStatement basicStatement, IfThen ifThen, WhileDo whileDo) {
		this.basicStatement = basicStatement;
		this.ifThen = ifThen;
		this.whileDo = whileDo;
	}

	@Override
	public void accept(Visitor visitor) throws SemanticException {
		visitor.visit(this);
	}

}
