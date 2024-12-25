package enshud.s4.compiler;

public abstract class Visitor {
	public void visit(Program program) throws SemanticException {}
	public void visit(ProgramName programName) {}
	public void visit(Block block) throws SemanticException {}
	public void visit(VariableDeclaration variableDeclaration) throws SemanticException {}
	public void visit(VariableDeclarationGroup variableDeclarationGroup) throws SemanticException {}
	public void visit(VariableNameGroup variableNameGroup) throws SemanticException {}
	public void visit(VariableName variableName) throws SemanticException {}
	public void visit(Type type) throws SemanticException {}
	public void visit(StandardType standardType) {}
	public void visit(ArrayType arrayType) throws SemanticException {}
	public void visit(Int integer) throws SemanticException {}
	public void visit(Sign sign) {}
	public void visit(SubprogramDeclarationGroup subprogramDeclarationGroup) throws SemanticException {}
	public void visit(SubprogramDeclaration subprogramDeclaration) throws SemanticException {}
	public void visit(SubprogramHead subprogramHead) throws SemanticException {}
	public void visit(ProcedureName procedureName) {}
	public void visit(FormalParameter formalParameter) throws SemanticException {}
	public void visit(FormalParameterGroup formalParameterGroup) throws SemanticException {}
	public void visit(FormalParameterNameGroup formalParameterNameGroup) throws SemanticException {}
	public void visit(FormalParameterName formalParameterName) {}
	public void visit(ComplexStatement complexStatement) throws SemanticException {}
	public void visit(StatementGroup statementGroup) throws SemanticException {}
	public void visit(Statement statement) throws SemanticException {}
	public void visit(IfThen ifThen) throws SemanticException {}
	public void visit(ElseStatement elseStatement) throws SemanticException {}
	public void visit(WhileDo whileDo) throws SemanticException {}
	public void visit(BasicStatement basicStatement) throws SemanticException {}
	public void visit(AssignStatement assignStatement) throws SemanticException {}
	public void visit(LeftSide leftSide) throws SemanticException {}
	public void visit(Variable variable) throws SemanticException {}
	public void visit(NaturalVariable naturalVariable) throws SemanticException {}
	public void visit(VariableWithIndex variableWithIndex) throws SemanticException {}
	public void visit(Index index) throws SemanticException {}
	public void visit(ProcedureCallStatement procedureCallStatement) throws SemanticException {}
	public void visit(EquationGroup equationGroup) throws SemanticException {}
	public void visit(Equation equation) throws SemanticException {}
	public void visit(SimpleEquation simpleEquation) throws SemanticException {}
	public void visit(Term term) throws SemanticException {}
	public void visit(Factor factor) throws SemanticException {}
	public void visit(RelationalOperator relationalOperator) {}
	public void visit(AdditionalOperator additionalOperator) {}
	public void visit(MultipleOperator multipleOperator) {}
	public void visit(InputOutputStatement inputOutputStatement) throws SemanticException {}
	public void visit(VariableGroup variableGroup) throws SemanticException {}
	public void visit(Constant constant) {}
	public void visit(UnsignedInteger unsignedInteger) {}
}
