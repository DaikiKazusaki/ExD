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
    	
    	if (programName != null) {
            programName.accept(visitor);
        }
        if (block != null) {
            block.accept(visitor);
        }
        if (complexStatement != null) {
            complexStatement.accept(visitor);
        }
    }
}
