package enshud.s4.compiler;

import java.util.List;

public class CompilationVisitor extends SemanticValidationVisitor {
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
    public void visit(Block block) throws SemanticException {
    	VariableDeclaration variableDeclaration = block.getVariableDeclaration();
    	SubprogramDeclarationGroup subprogramDeclarationGroup = block.getSubprogramDeclarationGroup();
    	
    	variableDeclaration.accept(this);
    	subprogramDeclarationGroup.accept(this);
    }
    
    @Override
    public void visit(VariableDeclaration variableDeclaration) throws SemanticException {
    	VariableDeclarationGroup variableDeclarationGroup = variableDeclaration.getVariableDeclarationGroup();
    	
    	if (variableDeclarationGroup != null) {
    		variableDeclarationGroup.accept(this);
    	}
    }
    
    @Override
    public void visit(VariableDeclarationGroup variableDeclarationGroup) throws SemanticException {
    	List<VariableNameGroup> variableNameGroupList = variableDeclarationGroup.getVariableNameGroupList();
    	List<Type> typeList = variableDeclarationGroup.getTypeList();
    	
    	for (int i = 0; i < variableNameGroupList.size(); i++) {
            variableNameGroupList.get(i).accept(this);
            typeList.get(i).accept(this);
        }
    }
    
    @Override
    public void visit(VariableNameGroup variableNameGroup) throws SemanticException {
    	List<VariableName> variableNameList = variableNameGroup.getVariableNameList();
    	
    	for (VariableName variableName: variableNameList) {
    		variableName.accept(this);
    	}
    }
    
    @Override
    public void visit(VariableName variableName) {}
    
    @Override
    public void visit(Type type) throws SemanticException {
    	StandardType standardType = type.getStandardType();
    	ArrayType arrayType = type.getArrayType();
    	
    	if (standardType != null) {
    		standardType.accept(this);
    	} else if (arrayType != null) {
    		arrayType.accept(this);
    	}
    }
    
    @Override
    public void visit(StandardType standardType) {}
    
    @Override
    public void visit(ArrayType arrayType) throws SemanticException {
    	Int minimumInteger = arrayType.getMinimumInteger();
    	Int maximumInteger = arrayType.getMinimumInteger();
    	StandardType standardType = arrayType.getStandardType();
    	
    	minimumInteger.accept(this);
    	maximumInteger.accept(this);
    	standardType.accept(this);
    }
    
    @Override
    public void visit(Int integer) throws SemanticException {
    	Sign sign = integer.getSign();
    	UnsignedInteger unsignedInteger = integer.getUnsignedInteger();
    	
    	if (sign != null) {
    		sign.accept(this);
    	}
    	unsignedInteger.accept(this);
    }
    
    @Override
    public void visit(Sign sign) {}
    
    @Override
    public void visit(SubprogramDeclarationGroup subprogramDeclarationGroup) throws SemanticException {
    	List<SubprogramDeclaration> subprogramDeclarationList = subprogramDeclarationGroup.getSubprogramDeclaration();
    	
    	for (SubprogramDeclaration subprogramDeclaration: subprogramDeclarationList) {
    		subprogramDeclaration.accept(this);
    	}
    }
    
    @Override
    public void visit(SubprogramDeclaration subprogramDeclaration) {}
    
    @Override
    public void visit(SubprogramHead subprogramHead) throws SemanticException {    	
    	ProcedureName procedureName = subprogramHead.getProcedureName();
    	FormalParameter formalParameter = subprogramHead.getFormalParameter();
    	
    	procedureName.accept(this);
    	formalParameter.accept(this);
    }
    
    @Override
    public void visit(ProcedureName procedureName) {}
    
    @Override
    public void visit(FormalParameter formalParameter) throws SemanticException {
    	FormalParameterGroup formalParameterGroup = formalParameter.getFormalParameterGroup();
    	
    	if (formalParameterGroup != null) {
    		formalParameterGroup.accept(this);
    	}
    }
    
    @Override
    public void visit(FormalParameterGroup formalParameterGroup) throws SemanticException {
    	List<FormalParameterNameGroup> formalParameterNameGroupList = formalParameterGroup.getFormalParameterNameGroupList();
    	List<StandardType> standardTypeList = formalParameterGroup.getStandardTypeList();
    	
    	for (int i = 0; i < formalParameterNameGroupList.size(); i++) {
    		FormalParameterNameGroup formalParameterNameGroup = formalParameterNameGroupList.get(i);
    		StandardType standardType = standardTypeList.get(i);
    		
    		formalParameterNameGroup.accept(this);
    		standardType.accept(this);
    	}
    }
    
    @Override
    public void visit(FormalParameterNameGroup formalParameterNameGroup) throws SemanticException {
    	List<FormalParameterName> formalParameterNameList = formalParameterNameGroup.getFormalParameterNameList();
    	
    	for (FormalParameterName formalParameterName: formalParameterNameList) {
    		formalParameterName.accept(this);
    	}
    }
    
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
