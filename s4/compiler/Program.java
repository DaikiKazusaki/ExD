package enshud.s4.compiler;

public class Program implements Node {
    private ProgramName programName;
    private Block block;
    private ComplexStatement complexStatement;

    public Program(ProgramName programName, Block block, ComplexStatement complexStatement) {
        this.programName = programName;
        this.block = block;
        this.complexStatement = complexStatement;
    }

    public ProgramName getProgramName() {
        return programName;
    }

    public Block getBlock() {
        return block;
    }

    public ComplexStatement getComplexStatement() {
        return complexStatement;
    }

    @Override
    public void accept(Visitor visitor) throws SemanticException {
        visitor.visit(this);
    }
}
