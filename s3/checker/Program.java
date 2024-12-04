package enshud.s3.checker;

public class Program implements Element {
    ProgramName programName;
    Block block;
    ComplexStatement complexStatement;
    
    @Override
    public void accept(Visitor visitor) {
    	visitor.visit(this);
    	programName.accept(visitor);
    	block.accept(visitor);
    	complexStatement.accept(visitor);
    }
}
