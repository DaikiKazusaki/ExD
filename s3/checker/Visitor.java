package enshud.s3.checker;

public abstract class Visitor {
    public void visit(Program program) {}
    public void visit(ProgramName programName) {}
    public void visit(Block block) {}
    public void visit(VariableDeclaration variableDeclaration) {}
    public void visit(VariableDeclarationGroup variableDeclarationGroup) throws SemanticException {}
    public void visit(VariableNameGroup variableNameGroup) {}
    public void visit(VariableName variableName) {}
    public void visit(Type type) {}
    public void visit(GeneralType generalType) {}
    public void visit(ArrayType arrayType) throws SemanticException {}
    public void visit(Int integer) {}
    public void visit(Sign sign) {}
    public void visit(SubprogramDeclarationGroup subprogramDeclarationGroup) {}
    public void visit(SubprogramDeclaration subprogramDeclaration) throws SemanticException {}
    public void visit(SubprogramHead subprogramHead) throws SemanticException {}
    public void visit(ProcedureName procedureName) {}
    public void visit(FormalParameter formalParameter) {}
    public void visit(FormalParameterGroup formalParameterGroup) throws SemanticException {}
    public void visit(FormalParameterNameGroup formalParameterNameGroup) {}
    public void visit(FormalParameterName formalParameterName) {}
    public void visit(ComplexStatement complexStatement) {}
    public void visit(StatementGroup statementGroup) {}
    public void visit(Statement statement) {}
    public void visit(Else elseStatement) {}
    public void visit(IfThen ifThen) throws SemanticException {}
    public void visit(WhileDo whileDo) {}
    public void visit(BasicStatement basicStatement) {}
    public void visit(AssignStatement assignStatement) throws SemanticException {}
    public void visit(LeftSide leftSide) throws SemanticException {}
    public void visit(Variable variable) {}
    public void visit(NaturalVariable naturalVariable) {}
    public void visit(VariableWithIndex variableWithIndex) {}
    public void visit(Index index) throws SemanticException {}
    public void visit(ProcedureCallStatement procedureCallStatement) throws SemanticException {}
    public void visit(EquationGroup equationGroup) {}
    public void visit(Equation equation) throws SemanticException {}
    public void visit(SimpleEquation simpleEquation) throws SemanticException {}
    public void visit(Term term) {}
    public void visit(Factor factor) {}
    public void visit(RelationalOperator relationalOperator) {}
    public void visit(AdditionalOperator additionalOperator) {}
    public void visit(MultipleOperator multipleOperator) {}
    public void visit(InputOutputStatement inputOutputStatement) {}
    public void visit(VariableGroup variableGroup) {}
    public void visit(Constant constant) {}
    public void visit(UnsignedInteger unsignedInteger) {}
}
