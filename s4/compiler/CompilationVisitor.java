package enshud.s4.compiler;

import java.util.ArrayList;
import java.util.List;

public class CompilationVisitor extends Visitor {
	private SymbolTable symbolTable;
	private boolean isNotFactor = false;
	private int stringNum = 0;
	private int conditionNum = -1; // 条件文のラベルの開始番地を保持する
	private int previousConditionNum = -1;
	private int nestNum = 0;  // 条件文(if, while)のネスト数を保持する
	private int procedureNum = 0;
	private String scope = "global";
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
    	complexStatement.accept(this);
    	
    	// サブルーチンのreturn先を確保
	    addOutputList('\t' + "RET");
    	
	    // 副プログラムの個数を揃える
	    procedureNum = 0;
    	block.accept(this);
    	
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
    public void visit(SubprogramDeclaration subprogramDeclaration) throws SemanticException {
    	SubprogramHead subprogramHead = subprogramDeclaration.getSubprogramHead();
    	VariableDeclaration variableDeclaration = subprogramDeclaration.getVariableDeclaration();
    	ComplexStatement complexStatement = subprogramDeclaration.getComplexStatement();
    	
    	subprogramHead.accept(this);
    	variableDeclaration.accept(this);
    	complexStatement.accept(this);
    }
    
    @Override
    public void visit(SubprogramHead subprogramHead) throws SemanticException {    	
    	ProcedureName procedureName = subprogramHead.getProcedureName();
    	FormalParameter formalParameter = subprogramHead.getFormalParameter();
    	
    	// スコープの変更
    	scope = subprogramHead.getProcedureName().getProcedureName();
    	
    	// サブルーチン開始
    	addOutputList("PROC" + String.valueOf(procedureNum) + '\t' + "NOP");
    	procedureNum++;
    	
    	// ローカル変数宣言の処理
    	addOutputList('\t' + "LD" + '\t' + "GR1, GR8");
    	String size = symbolTable.getSizeOfFormalParameter(scope);
    	addOutputList('\t' + "ADDA" + '\t' + "GR1, =" + size);
    	
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
    		
    		// 仮引数の処理
    		addOutputList('\t' + "LD" + '\t' + "GR2, 0, GR1");
    		addOutputList('\t' + "LAD" + '\t' + "GR3, 2");
    		addOutputList('\t' + "ST" + '\t' + "GR2, VAR, GR3");
    		addOutputList('\t' + "SUBA" + '\t' + "GR1, 1");
    		addOutputList('\t' + "LD" + '\t' + "GR1, 0, GR8");
    		addOutputList('\t' + "ADDA" + '\t' + "GR8, =1");
    		addOutputList('\t' + "ST" + '\t' + "GR1, 0, GR8");
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
    	
    	// 条件文のインデックスを更新
    	if (conditionNum == previousConditionNum) {
        	conditionNum += nestNum;
        	nestNum = 0;
        	previousConditionNum = conditionNum;
    	}
    }
    
    @Override
    public void visit(IfThen ifThen) throws SemanticException {
    	Equation equation = ifThen.getEquation();
    	ComplexStatement complexStatement = ifThen.getComplexStatement();
    	ElseStatement elseStatement = ifThen.getElseStatement();
    	
    	// 条件文の個数のインクリメント
    	conditionNum++;
    	nestNum++;
    	
    	// 条件式の探索
    	parseEquation(equation);
    	equation.accept(this);
    	
    	// 条件式の分岐を探索する
    	resolveCondition(equation);
    	
    	// 複合文の探索
    	addOutputList("BOTH" + String.valueOf(conditionNum) + '\t' + "NOP");
    	addOutputList('\t' + "PUSH" + '\t' + "0, GR1");
    	addOutputList('\t' + "POP" + '\t' + "GR1");
    	addOutputList('\t' + "CPL" + '\t' + "GR1, =#0000");
    	addOutputList('\t' + "JZE" + '\t' + "ELSE" + String.valueOf(conditionNum));
    	complexStatement.accept(this);
    	
    	// else文の探索
    	addOutputList("ELSE" + String.valueOf(conditionNum) + '\t' + "NOP");
    	if (elseStatement != null) {
        	elseStatement.accept(this);    		
    	}
    	
    	// 条件文の個数を元に戻す
    	conditionNum--;
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
    	
    	// 条件文の個数のインクリメント
    	conditionNum++;
    	nestNum++;
    	
    	// whileループ開始の番地を設定
    	addOutputList("WHILE" + String.valueOf(conditionNum) + '\t' + "NOP");
    	
    	// 条件式の探索
    	parseEquation(equation);
    	equation.accept(this);
    	
    	// 条件式の分岐を探索する
    	resolveCondition(equation);
    	
    	// 複合文の探索
    	addOutputList("BOTH" + String.valueOf(conditionNum) + '\t' + "NOP");
    	addOutputList('\t' + "PUSH" + '\t' + "0, GR1");
    	addOutputList('\t' + "POP" + '\t' + "GR1");
    	addOutputList('\t' + "CPL" + '\t' + "GR1, =#0000");
    	addOutputList('\t' + "JZE" + '\t' + "ENDWHL" + String.valueOf(conditionNum));
    	complexStatement.accept(this);
    	
    	// whileループ終了番地の設定
    	addOutputList('\t' + "JUMP" + '\t' + "WHILE" + String.valueOf(conditionNum));
    	addOutputList("ENDWHL" + String.valueOf(conditionNum) + '\t' + "NOP");
    	
    	// 条件文の個数を元に戻す
    	conditionNum--;
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
    	parseEquation(equation);
    	parseLeftSide(leftSide);
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
    	
    	// 式の並びを先に解析する
    	if (equationGroup != null) {
    		equationGroup.accept(this);
    		List<Equation> equationList = equationGroup.getEquationList();
        	for (Equation equation: equationList) {
        		parseEquation(equation);
        	}
    	}
    	
    	// サブルーチンを呼び出す
    	addOutputList('\t' + "CALL" + '\t' + "PROC" + String.valueOf(procedureNum));
    	procedureNum++;
    	
    	procedureName.accept(this);
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
    			resolveEquationTypeOfWrite(equation);
    		}
    		// 改行の処理
    	    addOutputList('\t' + "CALL" + '\t' + "WRTLN");
    	}
    }
    
    /**
     * 式の型を判定するメソッド
     * 
     * @param equation
     */
    private void resolveEquationTypeOfWrite(Equation equation) {
    	List<SimpleEquation> simpleEquationList = equation.getSimpleEquationList();
    	
    	for (SimpleEquation simpleEquation: simpleEquationList) {
    		List<Term> termList = simpleEquation.getTermList();
    		for (Term term: termList) {
    			List<Factor> factorList = term.getFactorList();
    			for (Factor factor: factorList) {
    				resolveFactorTypeOfWrite(factor);
    			}
    		}
    	}
    }
    
    /**
     * 因子の型を判定するメソッド
     * 
     * @param factor
     */
    private void resolveFactorTypeOfWrite(Factor factor) {
    	// [変数名，標準型, 配列型]
    	String[] variableAndType = null;
    	
    	if (factor.getVariable() != null) {
    		Variable variable = factor.getVariable();
    		variableAndType = getVariableType(variable);
    	} else if (factor.getConstant() != null) {
    		Constant constant = factor.getConstant();
    		variableAndType = getConstantType(constant);
    	} else if (factor.getEquation() != null) {
    		Equation equation = factor.getEquation();
    		resolveEquationTypeOfWrite(equation);
    	} else if (factor.getFactor() != null) {
    		Factor notFactor = factor.getFactor();
    		resolveFactorTypeOfWrite(notFactor);
    	}
    	
    	// サブルーチン(WRT)をCALL
    	if (variableAndType[1].equals("integer")) {
    		// integer型 -> WRTINTをCALL
    		writeInteger(variableAndType[0], variableAndType[2]);
    	} else if (variableAndType[1].equals("char")) {
    		String value = variableAndType[0];
    		if (variableAndType[2].equals("true")) {
    			writeString(value);
    		} else if (value.contains("'") && value.length() > 3) {
    			writeString(value);
    		} else {
    			writeChar(value);
    		}
    	}
    }
    
    /**
     * 変数の型を判定するメソッド
     * 
     * @param variable
     */
    private String[] getVariableType(Variable variable) {
    	String variableName= null, standardType = null, arrayType = null;
    	
    	if (variable.getNaturalVariable() != null) {
    		variableName = variable.getNaturalVariable().getVariableName().getVariableName();
    		standardType = symbolTable.containsNaturalVariable(variableName);
    		arrayType = "false";
    	} else if (variable.getVariableWithIndex() != null) {
    		variableName = variable.getVariableWithIndex().getVariableName().getVariableName();
    		standardType = symbolTable.containsVariableWithIndex(variableName);
    		arrayType = "true";
    		
    		// 式の判定
    		Equation equation = variable.getVariableWithIndex().getIndex().getEquation();
    		parseEquation(equation);
    	}

    	String[] type = {variableName, standardType, arrayType};
    	return type;
    }
    
    /**
     * 定数の型を取得するメソッド
     * 
     * @param constant
     * @return
     */
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
    	
    	// 定数に配列は含まないので，constantNameAndType[2] <- false
    	String[] constantNameAndType = {constantName, type, "false"};
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
     * @param variableName
     * @param isArray
     */
    private void writeInteger(String variableName, String isArray) {    	
    	if (isArray.equals("false")) {
    		// 純変数の場合
    		String address = symbolTable.getAddressOfSymbol(variableName, scope);
    		addOutputList('\t' + "LAD" + '\t' + "GR2, " + address);
    	} else {
    		// 添え字付き変数の場合
    		addOutputList('\t' + "POP" + '\t' + "GR2");
    		addOutputList('\t' + "ADDA" + '\t' + "GR2, =0");
    	}
    	
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
    
    /**
     * charを出力する
     * 
     */
    private void writeChar(String variableName) {
		String address = symbolTable.getAddressOfSymbol(variableName, scope);
    	addOutputList('\t' + "LAD" + '\t' + "GR2, " + address);
    	
    	// LDする
    	addOutputList('\t' + "LD" + '\t' + "GR1, VAR, GR2");
    	
    	// PUSHする
    	addOutputList('\t' + "PUSH" + '\t' + "0, GR1");
    	
    	// POPする
    	addOutputList('\t' + "POP" + '\t' + "GR2");
    	
    	// WRTCHをCALL
    	addOutputList('\t' + "CALL" + '\t' + "WRTCH");
    }
    
    /**
     * 式を解析するメソッド
     * 
     * @param equation
     */
    private void parseEquation(Equation equation) {
    	List<SimpleEquation> simpleEquationList = equation.getSimpleEquationList();
    	RelationalOperator relationalOperator = equation.getRelationalOperator();
    	
    	// 最初の単純式を解析
    	parseSimpleEquation(simpleEquationList.get(0));
    	
    	// 2個目の項を解析
    	if (simpleEquationList.size() == 2) {
    		parseSimpleEquation(simpleEquationList.get(1));
    		parseRelationalOperator(relationalOperator);
    	}
    }
    
    /**
     * 関係演算子を解析するメソッド
     * 
     * @param relationalOperator
     */
    private void parseRelationalOperator(RelationalOperator relationalOperator) {    	
    	// 一度比較を行う
    	addOutputList('\t' + "POP" + '\t' + "GR2");
    	addOutputList('\t' + "POP" + '\t' + "GR1");
    	addOutputList('\t' + "CPA" + '\t' + "GR1, GR2");
    }
    
    /**
     * 単純式を解析するメソッド
     * 
     * @param simpleEquation
     */
    private void parseSimpleEquation(SimpleEquation simpleEquation) {
    	List<Term> termList = simpleEquation.getTermList();
    	List<AdditionalOperator> additionalOperatorList = simpleEquation.getAdditionalOperatorList();
    	
    	// 最初の項のみを解析
    	parseTerm(termList.get(0));
		
    	// 2個目以降の項を解析
    	for (int i = 0; i < additionalOperatorList.size(); i++) {
    		parseTerm(termList.get(i + 1));
    		parseAdditionalOperator(additionalOperatorList.get(i));
    	}
    }
    
    /**
     * 加法演算子の解析を行うメソッド
     * 
     * @param additionalOperator
     */
    private void parseAdditionalOperator(AdditionalOperator additionalOperator) {
    	String add = additionalOperator.getAdditionalOperator();
    	
    	// スタックからPOPする
    	addOutputList('\t' + "POP" + '\t' + "GR2");
		addOutputList('\t' + "POP" + '\t' + "GR1");
    	
		// 演算子によって処理が変わる
    	if (add.equals("+")) {
    		addOutputList('\t' + "ADDA" + '\t' + "GR1, GR2");
    	} else if (add.equals("-")) {
    		addOutputList('\t' + "SUBA" + '\t' + "GR1, GR2");
    	} else if (add.equals("or")) {
    		addOutputList('\t' + "OR" + '\t' + "GR1, GR2");
    	}
    	
    	// PUSHする
    	addOutputList('\t' + "PUSH" + '\t' + "0, GR1");
    }
    
    /**
     * 項を解析するメソッド
     * 
     * @param term
     */
    private void parseTerm(Term term) {
    	List<Factor> factorList = term.getFactorList();
    	List<MultipleOperator> multipleOperatorList = term.getMultipleOperatorList();

		// 最初の項のみを解析
		parseFactor(factorList.get(0));
		
		// 2個目以降の項を解析
		if (factorList.size() > 1) {
			for (int i = 0; i < multipleOperatorList.size(); i++) {
				parseFactor(factorList.get(i + 1));
				parseMultipleOperator(multipleOperatorList.get(i));
			}
		}
    }
    
    /**
     * 乗法演算子の解析を行うメソッド
     * 
     * @param multipleOperator
     */
    private void parseMultipleOperator(MultipleOperator multipleOperator) {
    	String mul = multipleOperator.getMultipleOperator();
    	
    	// POPする
    	addOutputList('\t' + "POP" + '\t' + "GR2");
    	addOutputList('\t' + "POP" + '\t' + "GR1");
    	
    	if (mul.equals("*")) {
    		// MULTをCALL
    		addOutputList('\t' + "CALL" + '\t' + "MULT");
    		
    		// PUSHする
        	addOutputList('\t' + "PUSH" + '\t' + "0, GR2");
    	} else if (mul.equals("/") || mul.equals("div")) {
    		// DIVをCALL
    		addOutputList('\t' + "CALL" + '\t' + "DIV");
    		
    		// 商をPUSHする
        	addOutputList('\t' + "PUSH" + '\t' + "0, GR2");
    	} else if (mul.equals("mod")) {
    		// DIVをCALL
    		addOutputList('\t' + "CALL" + '\t' + "DIV");
    		
    		// 余りをPUSHする
        	addOutputList('\t' + "PUSH" + '\t' + "0, GR1");
    	} else if (mul.equals("and")) {
    		// ANDをとる
    		addOutputList('\t' + "AND" + '\t' + "GR1, GR2");
    		
    		// 結果をPUSHする
        	addOutputList('\t' + "PUSH" + '\t' + "0, GR1");
    	} 
    }
    
    /**
     * 因子を解析するメソッド
     * 
     * @param factor
     */
    private void parseFactor(Factor factor) {    	
    	if (factor.getVariable() != null) {
    		Variable variable = factor.getVariable();
    		parseVariable(variable, isNotFactor);
    	} else if (factor.getConstant() != null) {
    		Constant constant = factor.getConstant();
    		parseConstant(constant);
    	} else if (factor.getEquation() != null) {
    		Equation equation = factor.getEquation();
    		parseEquation(equation);
    	} else if (factor.getFactor() != null) {
    		isNotFactor = true;
    		Factor notFactor = factor.getFactor();
    		parseFactor(notFactor);
    		isNotFactor = false;
    	}
    }
    
    /**
     * 変数を解析するメソッド
     * 
     * @param variable
     */
    private void parseVariable(Variable variable, boolean isNotFactor) {
    	if (variable.getNaturalVariable() != null) {
    		// 代入する式をcaslにする
    		String varName = variable.getNaturalVariable().getVariableName().getVariableName();
    		String address = symbolTable.getAddressOfSymbol(varName, scope);
    		addOutputList('\t' + "LAD" + '\t' + "GR2, " + address);
    		addOutputList('\t' + "LD" + '\t' + "GR1, VAR, GR2");
    		if (isNotFactor) {
    			// notをとる
        		addOutputList('\t' + "PUSH" + '\t' + "0, GR1");
        		addOutputList('\t' + "POP" + '\t' + "GR1");
        		addOutputList('\t' + "XOR" + '\t' + "GR1, =#FFFF");
    		}
    		addOutputList('\t' + "PUSH" + '\t' + "0, GR1");
    	} else if (variable.getVariableWithIndex() != null) {
    		Equation equation = variable.getVariableWithIndex().getIndex().getEquation();
    		parseEquation(equation);
    	}
    }
    
    /**
     * 定数を解析するメソッド
     * 
     * @param 
     */
    private void parseConstant(Constant constant) {
    	String con = constant.getConstant();
    	
    	if (con.equals("true")) {
    		con = "#FFFF";
    	} else if (con.equals("false")) {
    		con = "#0000";
    	}
    	
    	// PUSHする
    	if (con.contains("'")) {
    		// 文字列の場合
    		addOutputList('\t' + "LD" + '\t' + "GR1, =" + con);
    		addOutputList('\t' + "PUSH" + '\t' + "0, GR1");
    	} else {
    		// 文字列以外の場合
    		addOutputList('\t' + "PUSH" + '\t' + con);
    	}
    }
    
    /**
     * 左辺を解析するメソッド
     * 
     * @param leftSide
     */
    private void parseLeftSide(LeftSide leftSide) {
    	if (leftSide.getVariable().getNaturalVariable() != null) {
    		// 代入する純変数(=レジスタ)を用意
    		String variableName = leftSide.getVariable().getNaturalVariable().getVariableName().getVariableName();
    		String address = symbolTable.getAddressOfSymbol(variableName, scope);
        	addOutputList('\t' + "LAD" + '\t' + "GR2, " + address);
    	} else if (leftSide.getVariable().getVariableWithIndex() != null) {
    		// 添え字の判定
    		Equation equation = leftSide.getVariable().getVariableWithIndex().getIndex().getEquation();
    		parseEquation(equation);
    		
    		// 代入する添え字付き変数(=レジスタ)を用意
    		addOutputList('\t' + "POP" + '\t' + "GR2");
    		addOutputList('\t' + "ADDA" + '\t' + "GR2, =0");
    	}
    	
    	// POPする
    	addOutputList('\t' + "POP" + '\t' + "GR1");
    	
    	// VAR番地に合計をストア
    	addOutputList('\t' + "ST" + '\t' + "GR1, VAR, GR2");
    }
    
    /**
     * 条件式のcaslコードを生成するメソッド
     * 
     * @param equaiton
     */
    private void resolveCondition(Equation equation) {
    	RelationalOperator relationalOperator = equation.getRelationalOperator();
    	if (relationalOperator != null) {
    		String rel = relationalOperator.getRelationalOperator();
    		
    		// 比較結果によって内容を変更する
        	if (rel.equals("=")) {
        		addOutputList('\t' + "JZE" + '\t' + "TRUE" + String.valueOf(conditionNum));
        	} else if (rel.equals("<>")) {
        		addOutputList('\t' + "JNZ" + '\t' + "TRUE" + String.valueOf(conditionNum));
        	} else if (rel.equals("<")) {
        		addOutputList('\t' + "JMI" + '\t' + "TRUE" + String.valueOf(conditionNum));
        	} else if (rel.equals("<=")) {
        		addOutputList('\t' + "JZE" + '\t' + "TRUE" + String.valueOf(conditionNum));
        		addOutputList('\t' + "JMI" + '\t' + "TRUE" + String.valueOf(conditionNum));
        	} else if (rel.equals(">")) {
        		addOutputList('\t' + "JPL" + '\t' + "TRUE" + String.valueOf(conditionNum));
        	} else if (rel.equals(">=")) {
        		addOutputList('\t' + "JZE" + '\t' + "TRUE" + String.valueOf(conditionNum));
        		addOutputList('\t' + "JPL" + '\t' + "TRUE" + String.valueOf(conditionNum));
        	}
        	
        	// 比較の処理
        	addOutputList('\t' + "LD" + '\t' + "GR1, =#0000");
        	addOutputList('\t' + "JUMP" + '\t' + "BOTH" + String.valueOf(conditionNum));
        	addOutputList("TRUE" + String.valueOf(conditionNum) + '\t' + "NOP");
        	addOutputList('\t' + "LD" + '\t' + "GR1, =#FFFF");
    	}
    }
}
