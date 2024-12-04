package enshud.s3.checker;

public class Program implements Element {
    private ProgramName programName;
    private Block block;
    private ComplexStatement complexStatement;
    
    @Override
    public void accept(Visitor visitor) {
    	visitor.visit(this);
    	programName.accept(visitor);
    	block.accept(visitor);
    	complexStatement.accept(visitor);
    }
}
