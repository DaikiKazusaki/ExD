package enshud.s4.compiler;

public abstract class Visitor {
	public void visit(Program program) throws SemanticException {}
	public void visit(ProgramName programName) {}
	public void visit(Block block) throws SemanticException {}
	public void visit(VariableDeclaration variableDeclaration) throws SemanticException {}
	public void visit(VariableDeclarationGroup variableDeclarationGroup) throws SemanticException {}
	public void visit(VariableNameGroup variableNameGroup) throws SemanticException {}
	public void visit(VariableName variableName) {}
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
	public void visit(FormalParameterNameGroup formalParameterNameGroup) {}
	public void visit(FormalParameterName formalParameterName) {}
	public void visit(ComplexStatement complexStatement) {}
	public void visit(StatementGroup statementGroup) {}
	public void visit(Statement statement) {}
	public void visit(IfThen ifThen) {}
	public void visit(ElseStatement elseStatement) {}
	public void visit(WhileDo whileDo) {}
	public void visit(BasicStatement basicStatement) {}
	public void visit(AssignStatement assignStatement) {}
	public void visit(LeftSide leftSide) {}
	public void visit(Variable variable) {}
	public void visit(NaturalVariable naturalVariable) {}
	public void visit(VariableWithIndex variableWithIndex) {}
	public void visit(Index index) {}
	public void visit(ProcedureCallStatement procedureCallStatement) {}
	public void visit(EquationGroup equationGroup) {}
	public void visit(Equation equation) {}
	public void visit(SimpleEquation simpleEquation) {}
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
