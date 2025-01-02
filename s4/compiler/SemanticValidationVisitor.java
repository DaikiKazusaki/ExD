package enshud.s4.compiler;

import java.util.ArrayList;
import java.util.List;

public class SemanticValidationVisitor extends Visitor {
	private SymbolTable symbolTable = new SymbolTable();
	private FunctionTable functionTable = new FunctionTable();
	private String scope = "global";
	
	public SymbolTable getSymbolTable() {
		return symbolTable;
	}
	
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
            
            prepareVariableForAddingSymbolTable(variableNameGroupList.get(i), typeList.get(i));
        }
    }

    /**
     * 記号表に変数を登録する準備をするメソッド
     * 
     * @param variableNameGroup
     * @param type
     * @param lineNum
     * @throws SemanticException
     */
    public void prepareVariableForAddingSymbolTable(VariableNameGroup variableNameGroup, Type type) throws SemanticException {
        String[] typeInfo = extractTypeInfo(type);
        String standardType = typeInfo[0];
        String isArray = typeInfo[1];
        String size = typeInfo[2];
        String lineNum = variableNameGroup.getLineNum();

        // 記号表に変数を登録
        for (VariableName variableName : variableNameGroup.getVariableNameList()) {
            addVariableToSymbolTable(variableName.getVariableName(), standardType, isArray, size, lineNum);
        }
    }

    /**
     * 型情報を抽出するヘルパーメソッド
     * 
     * @param type
     * @return [standardType, isArray, size]
     */
    private String[] extractTypeInfo(Type type) {
        String standardType, isArray, size;

        if (type.getStandardType() != null) {
            // 標準型
            standardType = type.getStandardType().getStandardType();
            isArray = "false";
            size = "1";
        } else {
            // 配列型
            standardType = type.getArrayType().getStandardType().getStandardType();
            isArray = "true";
            int maximumSize = Integer.parseInt(type.getArrayType().getMaximumInteger().getUnsignedInteger().getUnsignedInteger());
            int minimumSize = Integer.parseInt(type.getArrayType().getMinimumInteger().getUnsignedInteger().getUnsignedInteger());
            size = String.valueOf(maximumSize - minimumSize + 1);
        }

        return new String[]{standardType, isArray, size};
    }

    /**
     * 記号表に変数を追加するメソッド
     * 
     * @param variableName
     * @param type
     * @param isArray
     * @param size
     * @param lineNum
     * @throws SemanticException
     */
    public void addVariableToSymbolTable(String variableName, String type, String isArray, String size, String lineNum) throws SemanticException {
        if (symbolTable.isAbleToAddSymbolTable(variableName, scope)) {
            symbolTable.addSymbol(variableName, type, isArray, scope, size);
        } else {
            throw new SemanticException(lineNum);
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
    public void visit(VariableName variableName) throws SemanticException {}

    
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
    	
    	// ブロックの終了後はグローバル変数以外使用できない
    	scope = "global"; 
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
    	String lineNum = subprogramHead.getLineNum(); 
    	
    	ProcedureName procedureName = subprogramHead.getProcedureName();
    	FormalParameter formalParameter = subprogramHead.getFormalParameter();
    	
    	addFunctionTable(procedureName.getProcedureName(), lineNum);
    	
    	procedureName.accept(this);
    	formalParameter.accept(this);
    }
    
    /**
     * 関数表に副プログラム名を登録するメソッド
     * 
     * @param functionName
     * @param lineNum
     * @throws SemanticException
     */
    public void addFunctionTable(String functionName, String lineNum) throws SemanticException {   
    	if (functionTable.isAbleToAddFunctionTable(functionName)) {
    		functionTable.addFunctionTable(functionName);
    		// スコープ名の更新
    		scope = functionName;
    	} else {
    		throw new SemanticException(lineNum);
    	}
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
            
            // 仮引数を記号表に登録
            prepareFormalParameterForAddingSymbolTable(formalParameterNameGroup, standardType);
        }
    }

    /**
     * 記号表に仮引数を登録する準備をするメソッド
     * 
     * @param formalParameterNameGroup
     * @param standardType
     * @throws SemanticException
     */
    public void prepareFormalParameterForAddingSymbolTable(FormalParameterNameGroup formalParameterNameGroup, StandardType standardType) throws SemanticException {
        String type = standardType.getStandardType();
        String isArray = "false"; // 仮引数は配列ではないので，常にfalse
        String size = "1"; // 仮引数は配列ではないので，常に1
        String lineNum = formalParameterNameGroup.getLineNum();
        
        for (FormalParameterName formalParameterName : formalParameterNameGroup.getFormalParameterNameList()) {
            addVariableToSymbolTable(formalParameterName.getFormalParameterName(), type, isArray, size, lineNum);
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
    	
    	// if文の式の判定
    	String type = resolveType(equation);
    	if (!type.equals("boolean")) {
    		String lineNum = ifThen.getLineNum();
    		throw new SemanticException(lineNum);
    	}
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
    	
    	// if文の式の判定
    	String type = resolveType(equation);
    	if (!type.equals("boolean")) {
    		String lineNum = whileDo.getLineNum();
    		throw new SemanticException(lineNum);
    	}
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
    	
    	// 左辺と右辺の型を取得する
    	String lineNum = assignStatement.getLineNum();
    	String leftSideType = getLeftSideType(leftSide, lineNum);
    	String rightSideType = resolveType(equation);
    	if (!leftSideType.equals(rightSideType)) {
    		throw new SemanticException(lineNum);
    	}
    }
    
    /**
     * 左辺の型を判定するメソッド
     * 
     * @param leftSide
     * @return
     * @throws SemanticException
     */
    private String getLeftSideType(LeftSide leftSide, String lineNum) throws SemanticException {
    	String variableName, type = null;
    	
    	if (leftSide.getVariable().getNaturalVariable() != null) {
    		variableName = leftSide.getVariable().getNaturalVariable().getVariableName().getVariableName();
    		type = symbolTable.containsNaturalVariable(variableName);
    	} else if (leftSide.getVariable().getVariableWithIndex() != null) {
    		variableName = leftSide.getVariable().getVariableWithIndex().getVariableName().getVariableName();
    		type = symbolTable.containsVariableWithIndex(variableName);
    	}
    	
    	if (type == null) {
    		throw new SemanticException(lineNum);
    	}
    	
    	return type;
    }
    
    @Override
    public void visit(LeftSide leftSide) throws SemanticException {
    	Variable variable = leftSide.getVariable();
    	
    	variable.accept(this);
    	
    	// 変数の型を取得する
    	String variableName, type = null;
    	if (variable.getNaturalVariable() != null) {
    		variableName = variable.getNaturalVariable().getVariableName().getVariableName();
    		type = symbolTable.containsNaturalVariable(variableName);
    	} else if (variable.getVariableWithIndex() != null) {
    		variableName = variable.getVariableWithIndex().getVariableName().getVariableName();
    		type = symbolTable.containsVariableWithIndex(variableName);
    	}
    	
    	// 変数の型を確認
    	if (type == null) {
    		String lineNum = variable.getLineNum();
    		throw new SemanticException(lineNum);
    	}
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
    	
    	// 添え字の型の判定
    	// "integer"以外の変数，arrayならエラー
    	String type = resolveType(equation);
    	if (!type.equals("integer")) {
        	String lineNum = index.getLineNum();
        	throw new SemanticException(lineNum);
    	}
    }
    
    @Override
    public void visit(ProcedureCallStatement procedureCallStatement) throws SemanticException {
    	ProcedureName procedureName = procedureCallStatement.getProcedureName();
    	EquationGroup equationGroup = procedureCallStatement.getEquationGroup();
    	
    	procedureName.accept(this);
    	if (equationGroup != null) {
    		equationGroup.accept(this);
    	}
    	
    	// 手続き名が関数表に登録されているかの確認
    	String functionName = procedureName.getProcedureName();
    	List<String> functionList = functionTable.getFunctionTable();

    	if (!functionList.contains(functionName)) {
        	String lineNum = procedureCallStatement.getLineNum();
    		throw new SemanticException(lineNum);
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
    	
    	// 最初の項を確認
    	termList.get(0).accept(this);
    	
    	// 2個目以降の項を確認
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
    		variableGroup.accept(this);
    	} else if (equationGroup != null) {
    		equationGroup.accept(this);
    	}
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
     * 式の型を確認するメソッド
     * 
     * @param equation
     * @throws SemanticException
     * @return 
     */
    private String resolveType(Equation equation) throws SemanticException {
    	List<SimpleEquation> simpleEquationList = equation.getSimpleEquationList();
        List<String> termTypes = new ArrayList<>();
        
        for (SimpleEquation simpleEquation : simpleEquationList) {
            List<String> factorTypes = resolveTermTypes(simpleEquation);
            
            // 項の型を収集し、整合性をチェック
            checkTypeConsistency(factorTypes, simpleEquation.getLineNum());
            termTypes.add(factorTypes.get(0));
        }
        
        // 式全体の項の型を整合性チェック
        checkTypeConsistency(termTypes, equation.getLineNum());
        
        // 式の型を返却
        if (simpleEquationList.size() == 1) {
        	return termTypes.get(0);
        } else {
        	return "boolean";
        }
    }

    /**
     * 項の型を取得し、整合性をチェックするメソッド
     */
    private List<String> resolveTermTypes(SimpleEquation simpleEquation) throws SemanticException {
        List<String> factorTypes = new ArrayList<>();
        
        for (Term term : simpleEquation.getTermList()) {
            List<String> typesInTerm = resolveFactorTypes(term);
            
            // 項内の因子型をチェック
            checkTypeConsistency(typesInTerm, term.getLineNum());
            factorTypes.add(typesInTerm.get(0));
        }
        return factorTypes;
    }

    /**
     * 因子の型リストを取得するメソッド
     */
    private List<String> resolveFactorTypes(Term term) throws SemanticException {
        List<String> factorTypes = new ArrayList<>();
        for (Factor factor : term.getFactorList()) {
            String factorType = resolveType(factor);
            factorTypes.add(factorType);
        }
        return factorTypes;
    }

    /**
     * 因子の型を解決するメソッド
     */
    private String resolveType(Factor factor) throws SemanticException {
        if (factor.getVariable() != null) {
            return resolveVariableType(factor);
        } else if (factor.getConstant() != null) {
            return resolveConstantType(factor.getConstant().getConstant());
        } else if (factor.getEquation() != null) {
            return resolveType(factor.getEquation());
        } else if (factor.getFactor() != null) {
            return resolveType(factor.getFactor());
        } else {
        	throw new SemanticException(factor.getLineNum());
        }
    }

    /**
     * 変数の型を解決するメソッド
     */
    private String resolveVariableType(Factor factor) throws SemanticException {
        String varName = null, type = null;
        if (factor.getVariable().getNaturalVariable() != null) {
        	varName = factor.getVariable().getNaturalVariable().getVariableName().getVariableName();
        	type = symbolTable.containsNaturalVariable(varName);
        } else if (factor.getVariable().getVariableWithIndex() != null) {
        	varName = factor.getVariable().getVariableWithIndex().getVariableName().getVariableName();
        	type = symbolTable.containsVariableWithIndex(varName);
        }
        
        if (type == null) {
            throw new SemanticException(factor.getLineNum());
        }
        return type;
    }

    /**
     * 定数の型を解決するメソッド
     */
    private String resolveConstantType(String constant) {
        if ("true".equals(constant) || "false".equals(constant)) {
            return "boolean";
        } else if (constant.matches("\\d+")) {
            return "integer";
        } else {
            return "char";
        }
    }

    /**
     * 型の整合性を確認するメソッド
     */
    private void checkTypeConsistency(List<String> types, String lineNum) throws SemanticException {
        String baseType = types.get(0);
        for (String type : types) {
            if (!baseType.equals(type)) {
                throw new SemanticException(lineNum);
            }
        }
    }
}
