package enshud.s4.compiler;

public class CompilationVisitor extends Visitor {
	private WriteFile writeFile = new WriteFile();
	
    @Override
    public void visit(Program program) throws SemanticException {
    	ProgramName programName = program.getProgramName();
    	Block block = program.getBlock();
    	ComplexStatement complexStatement = program.getComplexStatement();
    	
    	programName.accept(this);
    	block.accept(this);
    	complexStatement.accept(this);
    }
    
    @Override
    public void visit(ProgramName programName) {}
    @Override
    public void visit(Block block) {}
    @Override
    public void visit(VariableDeclaration variableDeclaration) {}
    @Override
    public void visit(VariableDeclarationGroup variableDeclarationGroup) {}
    @Override
    public void visit(VariableNameGroup variableNameGroup) {}
    @Override
    public void visit(VariableName variableName) {}
    @Override
    public void visit(Type type) {}
    @Override
    public void visit(StandardType standardType) {}
    @Override
    public void visit(ArrayType arrayType) {}
    @Override
    public void visit(Int integer) {}
    @Override
    public void visit(Sign sign) {}
    @Override
    public void visit(SubprogramDeclarationGroup subprogramDeclarationGroup) {}
    @Override
    public void visit(SubprogramDeclaration subprogramDeclaration) {}
    @Override
    public void visit(SubprogramHead subprogramHead) {}
    @Override
    public void visit(ProcedureName procedureName) {}
    @Override
    public void visit(FormalParameter formalParameter) {}
    @Override
    public void visit(FormalParameterGroup formalParameterGroup) {}
    @Override
    public void visit(FormalParameterNameGroup formalParameterNameGroup) {}
    @Override
    public void visit(FormalParameterName formalParameterName) {}
    @Override
    public void visit(ComplexStatement complexStatement) {}
    @Override
    public void visit(StatementGroup statementGroup) {}
    @Override
    public void visit(Statement statement) {}
    @Override
    public void visit(IfThen ifThen) {}
    @Override
    public void visit(ElseStatement elseStatement) {}
    @Override
    public void visit(WhileDo whileDo) {}
    @Override
    public void visit(BasicStatement basicStatement) {}
    @Override
    public void visit(AssignStatement assignStatement) {}
    @Override
    public void visit(LeftSide leftSide) {}
    @Override
    public void visit(Variable variable) {}
    @Override
    public void visit(NaturalVariable naturalVariable) {}
    @Override
    public void visit(VariableWithIndex variableWithIndex) {}
    @Override
    public void visit(Index index) {}
    @Override
    public void visit(ProcedureCallStatement procedureCallStatement) {}
    @Override
    public void visit(EquationGroup equationGroup) {}
    @Override
    public void visit(Equation equation) {}
    @Override
    public void visit(SimpleEquation simpleEquation) {}
    @Override
    public void visit(Term term) {}
    @Override
    public void visit(Factor factor) {}
    @Override
    public void visit(RelationalOperator relationalOperator) {}
    @Override
    public void visit(AdditionalOperator additionalOperator) {}
    @Override
    public void visit(MultipleOperator multipleOperator) {}
    
    @Override
    public void visit(InputOutputStatement inputOutputStatement) {
    	if (inputOutputStatement.getVariableGroup() != null) {
    		// "readln"の場合
    	} else if (inputOutputStatement.getEquationGroup() != null) {
    		// "writeln"の場合
    		writeln();
    	}
    }
    
    /**
     * "writeln"の処理
     * GR1: 文字列の長さ，GR2: 文字列が格納されている先頭アドレス
     * 
     */
    public void writeln() {
    	String line;
    	
    	// 文字列の長さを取得する
    	line = '\t' + "LAD" + '\t' + "GR1, 4";
    	writeFile.addOutputList(line);
    	line = null;
    	
    	// GR1にPUSH
    	line = '\t' + "PUSH" + '\t' + "0, GR1" ;
    	writeFile.addOutputList(line);
    	line = null;
    	
    	// PUSHするアドレスを取得する
    	line = '\t' + "LAD" + '\t' + "GR2, CHAR0";
    	writeFile.addOutputList(line);
    	line = null;
    	
    	// GR2をPOPする
    	line = '\t' + "POP" + '\t' + "GR2";
    	writeFile.addOutputList(line);
    	line = null;
    	
    	// GR1をPOPする
    	line = '\t' + "POP" + '\t' + "GR2";
    	writeFile.addOutputList(line);
    	line = null;
    	
    	
    }
    
    @Override
    public void visit(VariableGroup variableGroup) {}
    @Override
    public void visit(Constant constant) {}
    @Override
    public void visit(UnsignedInteger unsignedInteger) {}
}
