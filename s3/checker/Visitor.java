package enshud.s3.checker;

public abstract class Visitor {
	public abstract void visit(Program program);
	public abstract void visit(ProgramName programName);
	public abstract void visit(Block block);
	public abstract void visit(VariableDeclaration vatiableDeclaration);
	public abstract void visit(VariableDeclarationGroup variableDeclarationGroup);
	public abstract void visit(VariableNameGroup variableNameGroup);
	public abstract void visit(VariableName variableName);
	public abstract void visit(Type type);
	public abstract void visit(GeneralType generalType);
	public abstract void visit(ArrayType arrayType);
	public abstract void visit(Integer integer);
	public abstract void visit(Sign sign);
	public abstract void visit(SubprogramDeclarationGroup subprogramDeclarationGroup);
	public abstract void visit(SubprogramDeclaration subprogramDeclaration);
	public abstract void visit(SubprogramHead subprogramHead);
	public abstract void visit(ProcedureName prodecureName);
	public abstract void visit(FormalParameter formalParameter);
	public abstract void visit(FormalParameterGroup formalParameterGroup);
	public abstract void visit(FormalParameterNameGroup formalParameterNameGroup);
	public abstract void visit(FormalParameterName formalParameterName);
	public abstract void visit(ComplexStatement complexStatement);
	public abstract void visit(StatementGroup statementGroup);
	public abstract void visit(Statement statement);
	public abstract void visit(IfThenElse ifThenElse);
	public abstract void visit(IfThen ifThen);
	public abstract void visit(WhileDo whileDo);
	public abstract void visit(BasicStatement basicStatement);
	public abstract void visit(AssignStatement assignStatement);
	public abstract void visit(LeftSide leftSide);
	public abstract void visit(Variable variable);
	// 純変数
	// public abstract void visit();
	// 添え字付き変数
	// public abstract void visit();
	// 添え字
	// public abstract void visit();
	public abstract void visit(ProcedureCallStatement procedureCallStatement);
	public abstract void visit(EquationGroup equationGroup);
	public abstract void visit(Equation equatioin);
	public abstract void visit(SimpleEquation simpleEquation);
	public abstract void visit(Term term);
	public abstract void visit(Factor factor);
	public abstract void visit(RelationalOperator relationalOperator);
	public abstract void visit(AdditionalOperator additionalOperator);
	public abstract void visit(MultipleOperator multipleOperator);
	public abstract void visit(InputOutputStatement inputoutputStatement);
	public abstract void visit(VariableGroup variableGroup);
	public abstract void visit(Constant constant);
	public abstract void visit(UnsignedInteger unsignedInteger);
	public abstract void visit(CharGroup charGroup);
	// 文字列要素
	// public abstract void visit();
	
}
