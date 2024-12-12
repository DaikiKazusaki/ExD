package enshud.s3.checker;

import java.util.HashMap;
import java.util.List;

public class ListVisitor extends Visitor {
	private SymbolTable symbolTable = new SymbolTable();
	private HashMap<String, String> variableTable = symbolTable.getSymbolTable();
	
	/**
	 * 変数，関数の宣言の処理
	 * 
	 */
	
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
	        
	        if (type.getGeneralType() != null) {
	            stringType = type.getGeneralType().getType();
	        } else {
	            stringType = type.getArrayType().getGeneralType().getType();
	            stringType = "array of " + stringType;
	        }

	        for (VariableName variableName : variableNameGroup.getVariableNameList()) {
	            String varName = variableName.getVariableName();
	            
	            // 同じ名前が記号表に存在するか確認
	            if (variableTable.containsKey(varName)) {
	                throw new SemanticException(lineNum);
	            }

	            // 記号表に追加
	            Symbol symbol = new Symbol(varName, stringType);
	            symbolTable.addSymbolTable(symbol.getName(), symbol.getType());
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
		new FunctionTable().addProcedureName(functionName);
	}
	
	/**
	 * 変数，関数の宣言の処理ここまで
	 * *********************************
	 * 以下は左辺値と右辺値の型の一致についての判定
	 * 
	 */
	
	/**
	 * 代入文
	 * 左辺の型と右辺の型を取り出す
	 * 
	 */
	public void visit(AssignStatement assignssStatement) throws SemanticException {
		
	}
	
	/**
	 * 手続き呼び出し文
	 * 関数表の中に手続き名が存在するか判定
	 * 
	 */
	public void visit(ProcedureCallStatement procedureCallStatement) throws SemanticException {
		List<String> functionTable = new FunctionTable().getFunctionTable();
		String functionName = procedureCallStatement.getProcedureName().getProcedureName();
		String lineNum = procedureCallStatement.getLineNum();
		
		if (!functionTable.contains(functionName)) {			
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
	 * 
	 */
	public void visit(SimpleEquation simpleEquation) {
		/*
		List<Term> term = simpleEquation.getTermList();
		
		for (int i = 0; i < term.size() - 2; i++) {
			Term leftTerm = term.get(i);
			Term rightTerm = term.get(i + 1);
			
			if (!leftTerm.equals(rightTerm)) {
				// エラー処理記述
			}
		}
		*/
	}
	
	/**
	 * 項
	 * 乗法演算子の個数分，因子同士の判定
	 * 
	 */
	public void visit(Term term) {
		/*
		List<Factor> factor = term.getFactorList();
		
		for (int i = 0; i < factor.size() - 2; i++) {
			Factor leftFactor = factor.get(i);
			Factor rightFactor = factor.get(i + 1);
			
			if (!leftFactor.equals(rightFactor)) {
				// エラー処理を記述
			}
		}
		*/
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
	public void visit(SubprogramDeclarationGroup subprogramDeclarationGroup) {}
	public void visit(SubprogramDeclaration subprogramDeclaration) {}
	public void visit(ProcedureName procedureName) {};
	public void visit(FormalParameter formalParameter) {}
	public void visit(FormalParameterNameGroup formalParamenterNameGroup) {}
	public void visit(FormalParameterName formalParameterName) {}
	public void visit(ComplexStatement complexStatement) {}
	public void visit(StatementGroup statementGroup) {}
	public void visit(Statement statement) {}
	public void visit(IfThen ifThen) {}
	public void visit(Else Else) {}
	public void visit(WhileDo whileDo) {}
	public void visit(BasicStatement basicStatement) {}
	public void visit(LeftSide leftSide) {}
	public void visit(Variable variable) {}
	public void visit(NaturalVariable naturalVariable) {}
	public void visit(VariableWithIndex variableWithIndex) {}
	public void visit(Index index) {}
	public void visit(EquationGroup equationGruop) {}
	public void visit(Factor factor) {}
	public void visit(RelationalOperator relationalOperator) {}
	public void visit(AdditionalOperator additionalOperator) {}
	public void visit(MultipleOperator multipleOperator) {}
	public void visit(InputOutputStatement inputOutputStatement) {}
	public void visit(VariableGroup variableGroup) {}
	public void visit(Constant constant) {}
}
