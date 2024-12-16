package enshud.s3.checker;

public class ProgramName implements Element {
	private String name;
	
	public ProgramName(String name) {
		this.name = name;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
	
	public String getProgramName() {
		return name;
	}
}
