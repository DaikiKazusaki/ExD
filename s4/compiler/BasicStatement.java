package enshud.s4.compiler;

public class BasicStatement implements Node {
    private AssignStatement assignStatement;
    private ProcedureCallStatement procedureCallStatement;
    private InputOutputStatement inputOutputStatement;
    private ComplexStatement complexStatement;

    public BasicStatement(AssignStatement assignStatement, ProcedureCallStatement procedureCallStatement, InputOutputStatement inputOutputStatement, ComplexStatement complexStatement) {
        this.assignStatement = assignStatement;
        this.procedureCallStatement = procedureCallStatement;
        this.inputOutputStatement = inputOutputStatement;
        this.complexStatement = complexStatement;
    }

    public AssignStatement getAssignStatement() {
        return assignStatement;
    }

    public ProcedureCallStatement getProcedureCallStatement() {
        return procedureCallStatement;
    }

    public InputOutputStatement getInputOutputStatement() {
        return inputOutputStatement;
    }

    public ComplexStatement getComplexStatement() {
        return complexStatement;
    }

    @Override
    public void accept(Visitor visitor) throws SemanticException {
        visitor.visit(this);
    }
}
