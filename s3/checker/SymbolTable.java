package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class SymbolTable {
	/**
	 * 記号表の情報
	 * [変数名，標準型，配列の判定，フォーマルパラメータの判定，スコープ，サイズ，使用済みかの判定，行番号]
	 * 
	 */
	public List<Symbol> symbolTable = new ArrayList<>();
	
	/**
	 * 記号表に変数を追加するメソッド
	 * scopeがglobal, 同じ関数名の時は，すでに変数が登録されていると判定する
	 * 
	 * @param name
	 * @return
	 */
	public boolean isAbleToAddSymbolTable(String variableName, String functionName) {
		boolean result = true;
		
		for (Symbol symbol: symbolTable) {
			String name = symbol.getVariableName();
			String scope = symbol.getScope();
			
			if (name.equals(variableName) && scope.equals(functionName)) {
				result = false;
				break;
			}
		}
		
		return result;
	}
	
	/**
	 * 記号表に追加するメソッド
	 * 
	 * @param symbol
	 */
	public void addSymbol(Symbol symbol) {
		symbolTable.add(symbol);
	}
	
	/**
	 * 記号表を取得するメソッド
	 * 
	 * @return
	 */
	public List<Symbol> getSymbolTable(){
		return symbolTable;
	}
	
	/**
	 * 記号表に登録されている純変数の確認をするメソッド
	 * 
	 * @param variableName
	 * @return
	 */
	public String containsNaturalVariable(String variableName, String functionName) {
		// スコープ内を探索
		for (Symbol symbol: symbolTable) {
			String name = symbol.getVariableName();
			boolean isArray = symbol.isArray();
			String scope = symbol.getScope();
			
			if (name.equals(variableName) && isArray == false && scope.equals(functionName)) {
				String type = symbol.getStandardType();
				return type;
			}
		}
		
		// グローバル変数を探索
		for (Symbol symbol: symbolTable) {
			String name = symbol.getVariableName();
			boolean isArray = symbol.isArray();
			String scope = symbol.getScope();
			
			if (name.equals(variableName) && isArray == false && scope.equals("global")) {
				String type = symbol.getStandardType();
				return type;
			}
		}
		
		return null;
	}
	
	/**
	 * 記号表に登録されている添え字付き変数の確認をするメソッド
	 * 
	 * @param variableName
	 * @return
	 */
	public String containsVariableWithIndex(String variableName, String functionName) {
		// スコープ内を探索
		for (Symbol symbol: symbolTable) {
			String name = symbol.getVariableName();
			boolean isArray = symbol.isArray();
			String scope = symbol.getScope();
			
			if (name.equals(variableName) && isArray == true && scope.equals(functionName)) {
				String type = symbol.getStandardType();
				return type;
			}
		}
		
		// グローバル変数を探索
		for (Symbol symbol: symbolTable) {
			String name = symbol.getVariableName();
			boolean isArray = symbol.isArray();
			String scope = symbol.getScope();
			
			if (name.equals(variableName) && isArray == true && scope.equals("global")) {
				String type = symbol.getStandardType();
				return type;
			}
		}
		
		return null;
	}
	
	/**
	 * 変数の型を返すメソッド
	 * 
	 * @param variableName
	 * @param scope
	 * @return
	 */
	public String getVariableType(String variableName, String functionName) {
		// スコープ内の探索
		for (Symbol symbol: symbolTable) {
			String name = symbol.getVariableName();
			String scope = symbol.getScope();
			if (name.equals(variableName) && scope.equals(functionName)) {
				return symbol.getStandardType();
			}
		}
		
		// 大域変数の探索
		for (Symbol symbol: symbolTable) {
			String name = symbol.getVariableName();
			String scope = symbol.getScope();
			if (name.equals(variableName) && scope.equals("global")) {
				return symbol.getStandardType();
			}
		}
		
		return null;
	}
}