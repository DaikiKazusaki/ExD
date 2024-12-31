package enshud.s4.compiler;

import java.util.ArrayList;
import java.util.List;

public class CompilationVisitor extends Visitor {
	private SymbolTable symbolTable;
	private int stringNum = 0;
	private List<String> outputStatementList = new ArrayList<>();
	private List<String> listForString = new ArrayList<>();
	
	public CompilationVisitor(SemanticValidationVisitor semanticValidationVisitor) {
		// 以下の3行はcaslには必ず必要
		outputStatementList.add("CASL" + '\t' + "START" + '\t' + "BEGIN");
		outputStatementList.add("BEGIN" + '\t' + "LAD" + '\t' + "GR6, 0");
		outputStatementList.add('\t' + "LAD" + '\t' + "GR7, LIBBUF");
		
		// 記号表の取得に必要なインスタンス
		this.symbolTable = semanticValidationVisitor.getSymbolTable();	
	}
	
	/**
	 * ファイルに書き込む内容を取得するメソッド
	 * 
	 * @return outputStatementList
	 */
	public List<String> getOutputStatementList(){
		return outputStatementList;
	}
	
	/**
	 * リストに代入するメソッド
	 * 
	 */
	public void addOutputList(String line) {
		outputStatementList.add(line);
	}
	
	/**
	 * 文字列を番地に置いておく
	 * 
	 */
	public void addString(String line) {
		listForString.add(line);
	}
	
    @Override
    public void visit(Program program) throws SemanticException {
    	ProgramName programName = program.getProgramName();
    	Block block = program.getBlock();
    	ComplexStatement complexStatement = program.getComplexStatement();
    	
    	programName.accept(this);
    	block.accept(this);
    	complexStatement.accept(this);
    	
    	// 変数の領域確保
    	String varSize = symbolTable.getSizeOfVar();
    	outputStatementList.add("VAR" + '\t' + "DS" + '\t' + varSize);
    	// 文字列の領域確保
    	outputStatementList.addAll(listForString);
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
    public void visit(ComplexStatement complexStatement) throws SemanticException {
    	StatementGroup statementGroup = complexStatement.getStatementGroup();
    	
    	statementGroup.accept(this);
    }
    
    @Override
    public void visit(StatementGroup statementGroup) throws SemanticException {
    	List<Statement> statementList = statementGroup.getStatementList();
    	
    	for (Statement statement: statementList) {
    		statement.accept(this);
    	}
    }
    
    @Override
    public void visit(Statement statement) throws SemanticException {
    	BasicStatement basicStatement = statement.getBasicStatement();
    	IfThen ifThen = statement.getIfThen();
    	WhileDo whileDo = statement.getWhileDo();
    	
    	if (basicStatement != null) {
    		basicStatement.accept(this);
    	} else if (ifThen != null) {
    		ifThen.accept(this);
    	} else if (whileDo != null) {
    		whileDo.accept(this);
    	}
    }
    
    @Override
    public void visit(IfThen ifThen) throws SemanticException {
    	Equation equation = ifThen.getEquation();
    	ComplexStatement complexStatement = ifThen.getComplexStatement();
    	
    	equation.accept(this);
    	complexStatement.accept(this);
    }
    
    @Override
    public void visit(ElseStatement elseStatement) throws SemanticException {
    	ComplexStatement complexStatement = elseStatement.getComplexStatement();
    	
    	complexStatement.accept(this);
    }
    
    @Override
    public void visit(WhileDo whileDo) throws SemanticException {
    	Equation equation = whileDo.getEquation();
    	ComplexStatement complexStatement = whileDo.getComplexStatement();
    	
    	equation.accept(this);
    	complexStatement.accept(this);
    }
    
    @Override
    public void visit(BasicStatement basicStatement) throws SemanticException {
    	AssignStatement assignStatement = basicStatement.getAssignStatement();
    	ProcedureCallStatement procedureCallStatement = basicStatement.getProcedureCallStatement();
    	InputOutputStatement inputOutputStatement = basicStatement.getInputOutputStatement();
    	ComplexStatement complexStatement = basicStatement.getComplexStatement();
    	
    	if (assignStatement != null) {
    		assignStatement.accept(this);
    	} else if (procedureCallStatement != null) {
    		procedureCallStatement.accept(this);
    	} else if (inputOutputStatement != null) {
    		inputOutputStatement.accept(this);
    	} else if (complexStatement != null) {
    		complexStatement.accept(this);
    	}
    } 
    
    @Override
    public void visit(AssignStatement assignStatement) throws SemanticException {
    	LeftSide leftSide = assignStatement.getLeftSide();
    	Equation equation = assignStatement.getEquation();
    	
    	leftSide.accept(this);
    	equation.accept(this);
    	
    	// 代入文の処理
    	writeAssign(leftSide, equation);
    }
    
    /**
     * 代入文のcaslコードを生成する
     * 
     */
    public void writeAssign(LeftSide leftSide, Equation equation) {    	
    	// スタックに代入する式をPUSH
    	addOutputList('\t' + "PUSH" + '\t' + "1");
    	
    	// 代入する変数(=レジスタ)を用意
    	addOutputList('\t' + "LAD" + '\t' + "GR2, 0");
    	
    	// 
    	addOutputList('\t' + "POP" + '\t' + "GR1");
    	
    	// VAR番地に合計をストア
    	addOutputList('\t' + "ST" + '\t' + "GR1, VAR, GR2");
    }
    
    @Override
    public void visit(LeftSide leftSide) throws SemanticException {
    	Variable variable = leftSide.getVariable();
    	
    	variable.accept(this);
    }
    
    @Override
    public void visit(Variable variable) throws SemanticException {
    	NaturalVariable naturalVariable = variable.getNaturalVariable();
    	VariableWithIndex variableWithIndex = variable.getVariableWithIndex();
    	
    	if (naturalVariable != null) {
    		naturalVariable.accept(this);
    	} else if (variableWithIndex != null) {
    		variableWithIndex.accept(this);
    	}
    }
    
    @Override
    public void visit(NaturalVariable naturalVariable) throws SemanticException {
    	VariableName variableName = naturalVariable.getVariableName();
    	
    	variableName.accept(this);
    }
    
    @Override
    public void visit(VariableWithIndex variableWithIndex) throws SemanticException {
    	VariableName variableName = variableWithIndex.getVariableName();
    	Index index = variableWithIndex.getIndex();
    	
    	variableName.accept(this);
    	index.accept(this);
    }
    
    @Override
    public void visit(Index index) throws SemanticException {
    	Equation equation = index.getEquation();
    	
    	equation.accept(this);
    }
    
    @Override
    public void visit(ProcedureCallStatement procedureCallStatement) throws SemanticException {
    	ProcedureName procedureName = procedureCallStatement.getProcedureName();
    	EquationGroup equationGroup = procedureCallStatement.getEquationGroup();
    	
    	procedureName.accept(this);
    	if (equationGroup != null) {
    		equationGroup.accept(this);
    	}
    }
    
    @Override
    public void visit(EquationGroup equationGroup) throws SemanticException {
    	List<Equation> equationList = equationGroup.getEquationList();
    	
    	for (Equation equation: equationList) {
    		equation.accept(this);
    	}
    }
    
    @Override
    public void visit(Equation equation) throws SemanticException {
    	List<SimpleEquation> simpleEquationList = equation.getSimpleEquationList();
    	RelationalOperator relationalOperator = equation.getRelationalOperator();
    	
    	simpleEquationList.get(0).accept(this);
    	if (relationalOperator != null) {
    		relationalOperator.accept(this);
    		simpleEquationList.get(1).accept(this);
    	}
    }
    
    @Override
    public void visit(SimpleEquation simpleEquation) throws SemanticException {
    	Sign sign = simpleEquation.getSign();
    	List<Term> termList = simpleEquation.getTermList();
    	List<AdditionalOperator> additionalOperatorList = simpleEquation.getAdditionalOperatorList();
    	
    	if (sign != null) {
    		sign.accept(this);
    	} 
    	termList.get(0).accept(this);
    	for (int i = 0; i < additionalOperatorList.size(); i++) {
    		additionalOperatorList.get(i).accept(this);
    		termList.get(i + 1).accept(this);
    	}
    }
    
    @Override
    public void visit(Term term) throws SemanticException {
    	List<Factor> factorList = term.getFactorList();
    	List<MultipleOperator> multipleOperatorList = term.getMultipleOperatorList();
    	
    	factorList.get(0).accept(this);
    	for (int i = 0; i < multipleOperatorList.size(); i++) {
    		multipleOperatorList.get(i).accept(this);
    		factorList.get(i + 1).accept(this);
    	}
    }
    
    @Override
    public void visit(Factor factor) throws SemanticException {
    	Variable variable = factor.getVariable();
    	Constant constant = factor.getConstant();
    	Equation equation = factor.getEquation();
    	Factor notFactor = factor.getFactor();
    	
    	if (variable != null) {
    		variable.accept(this);
    	} else if (constant != null) {
    		constant.accept(this);
    	} else if (equation != null) {
    		equation.accept(this);
    	} else if (notFactor != null) {
    		notFactor.accept(this);
    	}
    }
    
    @Override
    public void visit(RelationalOperator relationalOperator) {}
    
    @Override
    public void visit(AdditionalOperator additionalOperator) {}
    
    @Override
    public void visit(MultipleOperator multipleOperator) {}
    
    @Override
    public void visit(InputOutputStatement inputOutputStatement) throws SemanticException {
    	VariableGroup variableGroup = inputOutputStatement.getVariableGroup();
    	EquationGroup equationGroup = inputOutputStatement.getEquationGroup();
    	
    	if (variableGroup != null) {
    		// readlnの場合
    		variableGroup.accept(this);
    	} else if (equationGroup != null) {
    		// writelnの場合
    		equationGroup.accept(this);
    		
    		List<Equation> equationList = equationGroup.getEquationList();
    		for (int i = 0; i < equationList.size(); i++) {
    			Equation equation = equationList.get(i);
    			resolveEquationType(equation);
    		}
    		// 改行の処理
    	    addOutputList('\t' + "CALL" + '\t' + "WRTLN");
    	    addOutputList('\t' + "RET");
    	}
    }
    
    /**
     * 式の型を判定するメソッド
     * 
     * @param equation
     */
    private void resolveEquationType(Equation equation) {
    	List<SimpleEquation> simpleEquationList = equation.getSimpleEquationList();
    	
    	for (SimpleEquation simpleEquation: simpleEquationList) {
    		List<Term> termList = simpleEquation.getTermList();
    		for (Term term: termList) {
    			List<Factor> factorList = term.getFactorList();
    			for (Factor factor: factorList) {
    				resolveFactorType(factor);
    			}
    		}
    	}
    }
    
    /**
     * 因子の型を判定するメソッド
     * 
     * @param factor
     */
    private void resolveFactorType(Factor factor) {
    	String[] variableAndType = null;
    	
    	if (factor.getVariable() != null) {
    		Variable variable = factor.getVariable();
    		variableAndType = getVariableType(variable);
    	} else if (factor.getConstant() != null) {
    		Constant constant = factor.getConstant();
    		variableAndType = getConstantType(constant);
    	} else if (factor.getEquation() != null) {
    		Equation equation = factor.getEquation();
    		resolveEquationType(equation);
    	} else if (factor.getFactor() != null) {
    		Factor notFactor = factor.getFactor();
    		resolveFactorType(notFactor);
    	}
    	
    	if (variableAndType[1].equals("integer")) {
    		writeInteger(variableAndType[0]);
    	} else if (variableAndType[1].equals("char")) {
    		writeString(variableAndType[0]);
    	}
    }
    
    /**
     * 変数の型を判定するメソッド
     * 
     * @param variable
     */
    private String[] getVariableType(Variable variable) {
    	String variableName = null, type = null;
    	
    	if (variable.getNaturalVariable() != null) {
    		variableName = variable.getNaturalVariable().getVariableName().getVariableName();
    		type = symbolTable.containsNaturalVariable(variableName);
    	} else if (variable.getVariableWithIndex() != null) {
    		variableName = variable.getVariableWithIndex().getVariableName().getVariableName();
    		type = symbolTable.containsVariableWithIndex(variableName);
    	}

    	String[] variableNameAndType = {variableName, type};
    	return variableNameAndType;
    }
    
    private String[] getConstantType(Constant constant) {
    	String constantName = constant.getConstant();
    	String type = null;
    	
    	if (constantName.equals("true") || constantName.equals("false")) {
    		type = "boolean";
    	} else if (constantName.matches("\\d+")) {
    		type = "integer";
    	} else {
    		type = "char";
    	}
    	
    	String[] constantNameAndType = {constantName, type};
    	return constantNameAndType;
    }
    
    @Override
    public void visit(VariableGroup variableGroup) throws SemanticException {
    	List<Variable> variableList = variableGroup.getVariableList();
    	
    	for (Variable variable: variableList) {
    		variable.accept(this);
    	}
    }
    
    @Override
    public void visit(Constant constant) {}
    
    @Override
    public void visit(UnsignedInteger unsignedInteger) {}
    
    /**
     * WRTINTをcaslファイルに書き込む
     * 
     * @param integer
     */
    private void writeInteger(String integer) {
    	// GR2に0を代入
    	addOutputList('\t' + "LAD" + '\t' + "GR2, 0");
    	
    	// GR1にロード
    	addOutputList('\t' + "LD" + '\t' + "GR1, VAR, GR2");
    	
    	// PUSHする
    	addOutputList('\t' + "PUSH" + '\t' + "0, GR1");
    	
    	// POPする
    	addOutputList('\t' + "POP" + '\t' + "GR2");
    	
    	// WRTINTをCALL
    	addOutputList('\t' + "CALL" + '\t' + "WRTINT");
    }
    
    /**
     * WRTSTRをcaslファイルに書き込む
     * 
     * @param string
     */
    private void writeString(String string) {
    	// 文字列の長さを取得
    	String length = String.valueOf(string.length());
    	if (string.contains("'")) {
    		length = String.valueOf(Integer.valueOf(length) - 2);
    	}
    	addOutputList('\t' + "LAD" + '\t' + "GR1, " + length);
    	
    	// GR1にPUSH
    	addOutputList('\t' + "PUSH" + '\t' + "0, GR1");
    	
    	// PUSHするアドレスを取得する
    	String address = "CHAR" + String.valueOf(stringNum);
    	addOutputList('\t' + "LAD" + '\t' + "GR2, " + address);
    	
    	// PUSHする
    	addOutputList('\t' + "PUSH" + '\t' + "0, GR2");
    	
    	// GR2にPOPする
    	addOutputList('\t' + "POP" + '\t' + "GR2");
    	
    	// GR1にPOPする
    	addOutputList('\t' + "POP" + '\t' + "GR1");
    	
    	// WRTESTRをCALL
    	addOutputList('\t' + "CALL" + '\t' + "WRTSTR");
    	
    	// 出力する文字列を格納
    	addString(address + '\t' + "DC" + '\t' + string);
		
		// 次の文字列を出力するためのインクリメント
		stringNum++;
    }
}
