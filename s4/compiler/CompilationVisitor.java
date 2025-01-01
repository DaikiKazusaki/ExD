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
    			resolveEquationTypeOfWrite(equation);
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
    	String rel = relationalOperator.getRelationalOperator();
    	
    	if (rel.equals("=")) {
    		
    	} else if (rel.equals("<>")) {
    		
    	} else if (rel.equals("<")) {
    		
    	} else if (rel.equals("<=")) {
    		
    	} else if (rel.equals(">")) {
    		
    	} else if (rel.equals(">=")) {
    		
    	}
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
    	
    	if (mul.equals("*")) {
    		addOutputList('\t' + "CALL" + '\t' + "MULT");
    	} else if (mul.equals("/") || mul.equals("div")) {
    		
    	} else if (mul.equals("mod")) {
    		
    	} else if (mul.equals("and")) {
    		
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
    		parseVariable(variable);
    	} else if (factor.getConstant() != null) {
    		Constant constant = factor.getConstant();
    		parseConstant(constant);
    	} else if (factor.getEquation() != null) {
    		Equation equation = factor.getEquation();
    		parseEquation(equation);
    	} else if (factor.getFactor() != null) {
    		Factor notFactor = factor.getFactor();
    		parseFactor(notFactor);
    	}
    }
    
    /**
     * 変数を解析するメソッド
     * 
     * @param variable
     */
    private void parseVariable(Variable variable) {
    	if (variable.getNaturalVariable() != null) {
    		// 代入する式をcaslにする
    		addOutputList('\t' + "LAD" + '\t' + "GR2, 0");
    		addOutputList('\t' + "LD" + '\t' + "GR1, VAR, GR2");
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
    	
    	// PUSHする
    	addOutputList('\t' + "PUSH" + '\t' + con);
    }
    
    /**
     * 左辺を解析するメソッド
     * 
     * @param leftSide
     */
    private void parseLeftSide(LeftSide leftSide) {
    	if (leftSide.getVariable().getNaturalVariable() != null) {
    		// 代入する純変数(=レジスタ)を用意
        	addOutputList('\t' + "LAD" + '\t' + "GR2, 0");
    	} else if (leftSide.getVariable().getVariableWithIndex() != null) {
    		// 添え字の判定
    		Equation equation = leftSide.getVariable().getVariableWithIndex().getIndex().getEquation();
    		parseEquation(equation);
    		
    		// 代入する添え字付き変数(=レジスタ)を用意
    		addOutputList('\t' + "POP" + '\t' + "GR2");
    		addOutputList('\t' + "ADDA" + '\t' + "GR2, 0");
    	}
    	
    	// POPする
    	addOutputList('\t' + "POP" + '\t' + "GR1");
    	
    	// VAR番地に合計をストア
    	addOutputList('\t' + "ST" + '\t' + "GR1, VAR, GR2");
    }
}
