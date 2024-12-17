package enshud.s4.compiler;

public class Program implements Element {
	private ProgramName programName;
	private Block block;
	private ComplexStatement complexStatement;
	
	public Program(ProgramName programName, Block block, ComplexStatement complexStatement) {
		this.programName = programName;
		this.block = block;
		this.complexStatement = complexStatement;
	}
	
	@Override
	public void accept(Visitor visitor) throws SemanticException {
		visitor.visit(this);
	}
}
