package enshud.s4.compiler;

public class ProgramName implements Element {
	private String name;
	
	public ProgramName(String name) {
		this.name = name;
	}

	public String getProgramName() {
		return name;
	}

	@Override
	public void accept(Visitor visitor) throws SemanticException {
		visitor.visit(this);
	}
}
