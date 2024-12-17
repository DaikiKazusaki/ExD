package enshud.s4.compiler;

public class Sign implements Element {
	private String sign;

	public Sign(String sign) {
		this.sign = sign;
	}

	@Override
	public void accept(Visitor visitor) throws SemanticException {
		visitor.visit(this);
	}

}