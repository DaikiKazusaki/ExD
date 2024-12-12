package enshud.s3.checker;

import java.util.HashMap;
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
	 * 副プログラム頭部
	 * 関数表に追加
	 * 
	 */
	public void visit(SubprogramHead subprogramHead) {
		// 手続き名を登録
		String functionName = subprogramHead.getProcedureName().getProcedureName();		
		functionTable.addProcedureName(functionName);
	}
	
	/**
	 * 代入文
	 * 左辺の型と右辺の型を取り出す
	 * 
	 */
	public void visit(AssignStatement assignssStatement) throws SemanticException {
		
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
		String indexWithArray = index.getEquation().getSimpleEquation().getTerm().getFactor().getVariable().getNaturalVariable().getVariableName().getVariableName();
		String type = symbolTable.getSymbolTable().get(indexWithArray).get(0);
		String lineNum = index.getLineNum();
		
		if (!type.equals("integer")) {
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
	 * 式
	 * 関係演算子がある場合のみ判定
	 * 
	 */
	public void visit(Equation equation) {
		// Map.containsKey(検索するキー)
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
			UnsignedInteger token = item.getFactor().getConstant().getUnsignedInteger();
			if (token == null) {
				throw new SemanticException(lineNum);
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
	public void visit(Integer integer) {}
	public void visit(Sign sign) {}
	public void visit(SubprogramDeclaration subprogramDeclaration) throws SemanticException {}
	public void visit(SubprogramDeclarationGroup subprogramDeclarationGroup) {}
	public void visit(ProcedureName procedureName) {};
	public void visit(FormalParameter formalParameter) {}
	public void visit(FormalParameterGroup formalParameterGroup) {}
	public void visit(FormalParameterNameGroup formalParamenterNameGroup) {}
	public void visit(FormalParameterName formalParameterName) {}
	public void visit(ComplexStatement complexStatement) {}
	public void visit(StatementGroup statementGroup) {}
	public void visit(Statement statement) {}
	public void visit(IfThen ifThen) {}
	public void visit(Else Else) {}
	public void visit(WhileDo whileDo) {}
	public void visit(BasicStatement basicStatement) {}
	public void visit(Variable variable) {}
	public void visit(NaturalVariable naturalVariable) {}
	public void visit(VariableWithIndex variableWithIndex) {}
	public void visit(EquationGroup equationGruop) {}
	public void visit(Factor factor) {}
	public void visit(RelationalOperator relationalOperator) {}
	public void visit(AdditionalOperator additionalOperator) {}
	public void visit(MultipleOperator multipleOperator) {}
	public void visit(InputOutputStatement inputOutputStatement) {}
	public void visit(VariableGroup variableGroup) {}
	public void visit(Constant constant) {}
}
