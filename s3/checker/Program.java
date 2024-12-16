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
    
    public Program getProgram() {
    	return new Program(programName, block, complexStatement);
    }
    
    @Override
    public void accept(Visitor visitor) throws SemanticException {
    	visitor.visit(this);
    	programName.accept(visitor);
    	block.accept(visitor);
    	complexStatement.accept(visitor);
    }
}
