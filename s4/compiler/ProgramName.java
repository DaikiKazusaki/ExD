package enshud.s4.compiler;

public class ProgramName implements Element {
	private String name;
	private String lineNum;
	
	public ProgramName(String name, String lineNum) {
		this.name = name;
		this.lineNum = lineNum;
	}

	public String getProgramName() {
		return name;
	}
	
	public String getLineNum() {
		return lineNum;
	}

	@Override
	public void accept(Visitor visitor) throws SemanticException {
		visitor.visit(this);
	}
}
