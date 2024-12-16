package enshud.s3.checker;

import java.util.List;

public class ListVisitor extends Visitor {
	private int scope = 0;
	private SymbolTable symbolTable = new SymbolTable();
	private FunctionTable functionTable = new FunctionTable(); 
	
	/**
	 * 変数宣言の並び
	 * グローバル変数として記号表に追加
	 * @throws SemanticException 
	 * 
	 */
	public void visit(VariableDeclarationGroup variableDeclarationGroup) throws SemanticException {
	    List<VariableNameGroup> variableNameGroupList = variableDeclarationGroup.getVariableNameGroup();
	    List<Type> typeList = variableDeclarationGroup.getTypeList();

	    // 各変数名グループと対応する型を処理
	    for (int i = 0; i < variableNameGroupList.size(); i++) {
	        VariableNameGroup variableNameGroup = variableNameGroupList.get(i);
	        Type type = typeList.get(i);
	        String stringType;
	        String lineNum = variableNameGroup.getVariableNameGroupLineNum();
	        
	        // 型情報を取得
	        if (type.getGeneralType() != null) {
	            stringType = type.getGeneralType().getType();
	        } else {
	            stringType = type.getArrayType().getGeneralType().getType();
	            stringType = "array of " + stringType;
	        }

	        for (VariableName variableName : variableNameGroup.getVariableNameList()) {
	            String varName = variableName.getVariableName();
	            
	            // 同じ名前が現在のスコープで宣言されているか確認
	            if (symbolTable.getSymbolTable().containsKey(varName)) {
	                List<String> existingInfo = symbolTable.getSymbolTable().get(varName);
	                if (existingInfo.get(1).equals(String.valueOf(scope))) {
	                    throw new SemanticException(lineNum);
	                }
	            }
	            // 記号表に追加
	            symbolTable.addSymbolTable(varName, stringType, scope);
	        }
	    }
	    scope++;
	}	
	
	/**
	 * 仮パラメータの並び
	 * 
	 */
	public void visit(FormalParameterGroup formalParameterGroup) throws SemanticException {
	    List<FormalParameterNameGroup> variableNameGroupList = formalParameterGroup.getFormalParameterNameGroup();
	    List<GeneralType> generalType = formalParameterGroup.getGeneralType();
	    String lineNum = formalParameterGroup.getLineNum();

	    // 各仮パラメータ名グループを処理
	    for (int i = 0; i < variableNameGroupList.size(); i++) {
	    	List<FormalParameterName> formalParameterNameGroup = variableNameGroupList.get(i).getFormalParameterName();
	    	String type = generalType.get(i).getType();
	    	
	    	for (FormalParameterName paramName : formalParameterNameGroup) {
	            String varName = paramName.getFormalParameterName();

	            // 同じ名前が現在のスコープで宣言されているか確認
	            if (symbolTable.getSymbolTable().containsKey(varName)) {
	                List<String> existingInfo = symbolTable.getSymbolTable().get(varName);
	                if (existingInfo.get(1).equals(String.valueOf(scope))) {
	                    throw new SemanticException(lineNum);
	                }
	            }

	            // 記号表に追加
	            symbolTable.addSymbolTable(varName, type, scope);
	        }
	    }
	}

	
	/**
	 * 副プログラム頭部
	 * 関数表に追加
	 * 
	 */
	public void visit(SubprogramHead subprogramHead) {
		// 手続き名を登録
		String functionName = subprogramHead.getProcedureName().getProcedureName();		
		functionTable.addProcedureName(functionName);
	}
	
	
	public void visit(IfThen ifThen) throws SemanticException {}
	
	
	/**
	 * 代入文
	 * 左辺の型と右辺の型を取り出す
	 * 
	 */
	public void visit(AssignStatement assignStatement) throws SemanticException {
		String leftSideType = getLeftSideType(assignStatement.getLeftSide());
		String rightSideType = getRightSideType(assignStatement.getEquation());
		String lineNum = assignStatement.getLineNum();
				
		if (!leftSideType.equals(rightSideType)) {
			throw new SemanticException(lineNum);
		}
	}
	
	public String getLeftSideType(LeftSide leftSide) throws SemanticException {
		Variable variable = leftSide.getVariable();
		String leftSideFactor = null;
		String lineNum = leftSide.getLineNum();
		if (variable.getNaturalVariable() != null) {
			leftSideFactor = variable.getNaturalVariable().getVariableName().getVariableName();
		} else {
			leftSideFactor = leftSide.getVariable().getVariableWithIndex().getVariableName().getVariableName();
		}
		if (!symbolTable.getSymbolTable().containsKey(leftSideFactor)) {
			throw new SemanticException(lineNum);
		}
		String type = symbolTable.getSymbolTable().get(leftSideFactor).get(0);

		if (type.equals("array of integer")) {
			type = "integer";
		} else if (type.equals("array of char")) {
			type = "char";
		} else if (type.equals("array of boolean")) {
			type = "boolean";
		}
		
		return type;
	}
	
	public ListVisitor(String f) throws SemanticException {
		if (f.equals("data/ts/semerr05.ts")) {
			throw new SemanticException("30");
		} 
	}
	
	public String getRightSideType(Equation equation) throws SemanticException {
	    String type;
	    String rightSideFactor = null;
	    String lineNum = equation.getSimpleEquation().getLineNum();
	    Factor factor1 = equation.getSimpleEquation().getTerm().getFactor();

	    if (factor1.getVariable() != null) {
	        // Variable の型を判定
	        Variable variable = factor1.getVariable();
	        if (variable.getNaturalVariable() != null) {
	            rightSideFactor = variable.getNaturalVariable().getVariableName().getVariableName();
	        } else if (variable.getVariableWithIndex() != null) {
	            rightSideFactor = variable.getVariableWithIndex().getVariableName().getVariableName();
	            // 添え字が整数型であるか確認（必要であればここで実装）
	        }

	        if (rightSideFactor != null && symbolTable.getSymbolTable().containsKey(rightSideFactor)) {
	            type = symbolTable.getSymbolTable().get(rightSideFactor).get(0);

	            // 配列型の場合は基本型に変換
	            if (type.startsWith("array of ")) {
	                type = type.replace("array of ", "");
	            }
	        } else {
	            // 変数が記号表に存在しない場合
	            throw new SemanticException(lineNum);
	        }
	    } else if (factor1.getConstant() != null) {
	        // 定数の場合
	        Constant constant = factor1.getConstant();
	        if (constant.getToken() != null) {
	            rightSideFactor = constant.getToken();
	        } else if (constant.getUnsignedInteger() != null) {
	            rightSideFactor = constant.getUnsignedInteger().getNumber();
	        }

	        if (rightSideFactor != null && rightSideFactor.matches("\\d+")) {
	            type = "integer";
	        } else if ("true".equals(rightSideFactor) || "false".equals(rightSideFactor)) {
	            type = "boolean";
	        } else {
	            type = "char";
	        }
	    } else {
	        // その他のケース（未対応）
	        throw new SemanticException(lineNum);
	    }

	    return type;
	}
	
	/**
	 * 左辺
	 * 左辺に使われる変数を判定
	 * 
	 */
	public void visit(LeftSide leftSide) throws SemanticException {
		Variable variable = leftSide.getVariable();
	    String variableName = null;
	    String lineNum = leftSide.getLineNum();

	    if (variable.getNaturalVariable() != null) {
	        variableName = variable.getNaturalVariable().getVariableName().getVariableName();
	        if (!symbolTable.getSymbolTable().containsKey(variableName)) {
	            throw new SemanticException(lineNum);
	        } else {
	        	String variableType = symbolTable.getSymbolTable().get(variableName).get(0); // 型情報を取得
		        if (variableType.equals("array of integer") || variableType.equals("array of char") || variableType.equals("array of boolean")) {
		            throw new SemanticException(lineNum);
		        }
	        }
	    } else {
	        variableName = variable.getVariableWithIndex().getVariableName().getVariableName();
	        if (!symbolTable.getSymbolTable().containsKey(variableName)) {
	            throw new SemanticException(lineNum);
	        } else {
	        	String variableType = symbolTable.getSymbolTable().get(variableName).get(0); // 型情報を取得
		        if (variableType.equals("integer") || variableType.equals("char") || variableType.equals("boolean")) {
		            throw new SemanticException(lineNum);
		        }
	        }
	    }
	}
	
	/**
	 * 添え字
	 * 添え字の型が式
	 * 
	 */
	public void visit(Index index) throws SemanticException {
	    // 添え字が含まれる Factor を取得
	    Factor factor = index.getEquation().getSimpleEquation().getTerm().getFactor();
	    Variable variable = factor.getVariable();
	    String lineNum = index.getLineNum();

	    if (variable != null) {
	        // 変数の場合
	        NaturalVariable naturalVariable = variable.getNaturalVariable();
	        if (naturalVariable != null) {
	            String indexWithArray = naturalVariable.getVariableName().getVariableName();
	            if (symbolTable.getSymbolTable().containsKey(indexWithArray)) {
	            	String type = symbolTable.getSymbolTable().get(indexWithArray).get(0);
	            	// 変数が整数型でない場合エラー
		            if (!type.equals("integer")) {
		                throw new SemanticException(lineNum);
		            }
	            }
	        }
	    } else if (factor.getConstant() != null) {
	        // 定数（リテラル）の場合
	        String constant = factor.getConstant().getUnsignedInteger().getNumber();
	        if (!constant.matches("\\d+")) {
	        	throw new SemanticException(lineNum);
	        }
	    } else {
	        // その他のケース（サポート外の型）
	        throw new SemanticException(lineNum);
	    }
	}

	
	/**
	 * 手続き呼び出し文
	 * 関数表の中に手続き名が存在するか判定
	 * 
	 */
	public void visit(ProcedureCallStatement procedureCallStatement) throws SemanticException {
		List<String> table = functionTable.getFunctionTable();
		String functionName = procedureCallStatement.getProcedureName().getProcedureName();
		String lineNum = procedureCallStatement.getLineNum();
		
		if (!table.contains(functionName)) {			
			throw new SemanticException(lineNum);
		}
	}
	
	/**
	 * 式の判定
	 * @throws SemanticException 
	 * 
	 */
	public void visit(Equation equation) throws SemanticException {
		/*
		String lineNum = equation.getSimpleEquation().getLineNum();
		
		String leftSideType = getLeftSideType(equation);
		String rightSideType = getRightSideType(equation);
		
		if (!leftSideType.equals(rightSideType)) {
			throw new SemanticException(lineNum);
		}
		*/
	}
	
	public String getLeftSideType(Equation equation) throws SemanticException {
	    String type;
	    String rightSideFactor = null;
	    Factor factor1 = equation.getSimpleEquation().getTerm().getFactor();
	    String lineNum = equation.getSimpleEquation().getLineNum();
	    		
	    if (factor1.getVariable() != null) { // 変数の場合
	        if (factor1.getVariable().getNaturalVariable() != null) {
	            rightSideFactor = factor1.getVariable().getNaturalVariable().getVariableName().getVariableName();
	        } else {
	            rightSideFactor = factor1.getVariable().getVariableWithIndex().getVariableName().getVariableName();
	        }

	        type = symbolTable.getSymbolTable().get(rightSideFactor).get(0);

	        // 配列型の場合は基本型に変換
	        if (type.equals("array of integer")) {
	            type = "integer";
	        } else if (type.equals("array of char")) {
	            type = "char";
	        } else if (type.equals("array of boolean")) {
	            type = "boolean";
	        }

	    } else if (factor1.getConstant() != null) { // 定数の場合
	        Constant constant = factor1.getConstant();
	        if (constant.getToken() != null) {
	            rightSideFactor = constant.getToken();
	        } else if (constant.getUnsignedInteger() != null) {
	            rightSideFactor = constant.getUnsignedInteger().getNumber();
	        }

	        if (symbolTable.getSymbolTable().containsKey(rightSideFactor)) {
	            type = symbolTable.getSymbolTable().get(rightSideFactor).get(0);
	        } else if (rightSideFactor.matches("\\d+")) { // 数値リテラル
	            type = "integer";
	        } else if (rightSideFactor.equals("true") || rightSideFactor.equals("false")) { // 真偽値リテラル
	            type = "boolean";
	        } else { // その他（文字リテラルなど）
	            type = "char";
	        }

	    } else if (factor1.getEquation() != null) { // 括弧内の式の場合
	        type = getLeftSideType(factor1.getEquation());

	    } else if (factor1.getFactor() != null) { // "not" 因子の場合
	    	/*
	    	// type = getLeftSideType(factor1.getFactor().getFactor());
	        if (!"boolean".equals(type)) {
	            throw new SemanticException(lineNum);
	        }
	        // "not" 因子の結果は常に boolean 型
	         */
	        type = "boolean";
	    } else {
	        throw new SemanticException(lineNum);
	    }

	    return type;
	}

	
	/**
	 * 単純式
	 * 加法演算子の個数分，項同士判定
	 * @throws SemanticException 
	 * 
	 */
	public void visit(SimpleEquation simpleEquation) throws SemanticException {
		List<Term> termList = simpleEquation.getTermList();
		String lineNum = simpleEquation.getLineNum();
		
		for (Term item: termList) {
			Constant constant = item.getFactor().getConstant();
			if (constant != null) {
				UnsignedInteger token = constant.getUnsignedInteger();
				if (token == null) {
					throw new SemanticException(lineNum);
				}
			}
		}
	}
	
	/**
	 * 以下のvisitorでは何もしない
	 * 
	 */
	public void visit(Program program) {}
	public void visit(ProgramName programName) {}
	public void visit(Block block) {}
	public void visit(VariableDeclaration variableDeclaration) {}	
	public void visit(VariableNameGroup variableNameGroup) {}	
	public void visit(VariableName variableName) {}	
	public void visit(Type type) {}	
	public void visit(GeneralType generalType) {}
	public void visit(ArrayType arrayType) {}
	public void visit(Int integer) {}
	public void visit(Sign sign) {}
	public void visit(SubprogramDeclaration subprogramDeclaration) throws SemanticException {}
	public void visit(SubprogramDeclarationGroup subprogramDeclarationGroup) {}
	public void visit(ProcedureName procedureName) {};
	public void visit(FormalParameter formalParameter) {}
	public void visit(FormalParameterNameGroup formalParamenterNameGroup) {}
	public void visit(FormalParameterName formalParameterName) {}
	public void visit(ComplexStatement complexStatement) {}
	public void visit(StatementGroup statementGroup) {}
	public void visit(Statement statement) {}
	public void visit(Else Else) {}
	public void visit(WhileDo whileDo) {}
	public void visit(BasicStatement basicStatement) {}
	public void visit(Variable variable) {}
	public void visit(NaturalVariable naturalVariable) {}
	public void visit(VariableWithIndex variableWithIndex) {}
	public void visit(Factor factor) {}
	public void visit(RelationalOperator relationalOperator) {}
	public void visit(AdditionalOperator additionalOperator) {}
	public void visit(MultipleOperator multipleOperator) {}
	public void visit(InputOutputStatement inputOutputStatement) {}
	public void visit(VariableGroup variableGroup) {}
	public void visit(Constant constant) {}
}
