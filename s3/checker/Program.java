package enshud.s3.checker;

public class Program implements Element {
    private ProgramName programName;
    private Block block;
    private ComplexStatement complexStatement;
    
    public Program(ProgramName programName, Block block, ComplexStatement complexStatement) {
    	this.programName = programName;
    	this.block = block;
    	this.complexStatement = complexStatement;
    }
    
    public void accept(Visitor visitor) {
    	visitor.visit(this);
    }
}
