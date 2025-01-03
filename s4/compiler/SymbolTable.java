package enshud.s4.compiler;

import java.util.ArrayList;
import java.util.List;

public class SymbolTable {
	/**
	 * 記号表の情報
	 * [変数名，標準型，配列の判定，添え字付き変数の判定，スコープ，サイズ]
	 * 
	 */
	public List<List<String>> symbolTable = new ArrayList<>();
	private int NAMECOLS = 0;
	private int STANDARDTYPECOLS = 1;
	private int ISARRAYCOLS = 2; 
	private int SCOPECOLS = 3;
	private int SIZECOLS = 4;
	
	/**
	 * 記号表に登録するメソッド
	 * 
	 * @param name
	 * @param type
	 * @param isArray
	 * @param scope
	 */
	public void addSymbol(String name, String type, String isArray, String scope, String size) {		
		List<String> newSymbolInformation = new ArrayList<>();
		newSymbolInformation.add(name);
		newSymbolInformation.add(type);
		newSymbolInformation.add(isArray);
		newSymbolInformation.add(scope);
		newSymbolInformation.add(size);
		
		symbolTable.add(newSymbolInformation);
	}
	
	/**
	 * 記号表に変数を追加するメソッド
	 * scopeがglobal or 同じ関数名の時は，すでに変数が登録されていると判定する
	 * 
	 * @param name
	 * @return
	 */
	public boolean isAbleToAddSymbolTable(String name, String scope) {
		boolean result = true;
		
		for (int i = 0; i < symbolTable.size(); i++) {
			if (symbolTable.get(i).get(NAMECOLS).equals(name) && symbolTable.get(i).get(SCOPECOLS).equals(scope))
			result = false;
		}
		
		return result;
	}
	
	/**
	 * 記号表を取得するメソッド
	 * 
	 * @return
	 */
	public List<List<String>> getSymbolTable(){
		return symbolTable;
	}
	
	/**
	 * 記号表に登録されている純変数の確認をするメソッド
	 * 
	 * @param variableName
	 * @return
	 */
	public String containsNaturalVariable(String variableName) {
		for (int i = 0; i < symbolTable.size(); i++) {
			if (symbolTable.get(i).get(NAMECOLS).equals(variableName) && symbolTable.get(i).get(ISARRAYCOLS).equals("false")) {
				return symbolTable.get(i).get(STANDARDTYPECOLS);
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
	public String containsVariableWithIndex(String variableName) {
		for (int i = 0; i < symbolTable.size(); i++) {
			if (symbolTable.get(i).get(NAMECOLS).equals(variableName) && symbolTable.get(i).get(ISARRAYCOLS).equals("true")) {
				return symbolTable.get(i).get(STANDARDTYPECOLS);
			} 
		}
		return null;
	}
	
	/**
	 * 記号表の大きさを取得するメソッド
	 * 
	 * @return
	 */
	public String getSizeOfVar() {
		int sum = 0;
		for (int i = 0; i < symbolTable.size(); i++) {
			int size = Integer.valueOf(symbolTable.get(i).get(SIZECOLS));
			sum += size;
		}
		
		return String.valueOf(sum);
	}
	
	/**
	 * 各ローカル変数の大きさを取得するメソッド
	 * 
	 * @return
	 */
	public String getAddressOfSymbol(String varName, String scope) {
		int address = 0;
		
		for (int i = 0; i < symbolTable.size(); i++) {
			String variableName = symbolTable.get(i).get(NAMECOLS);
			String functionName = symbolTable.get(i).get(SCOPECOLS);
			if (varName.equals(variableName) && scope.equals(functionName)) {
				break;
			} else {
				String size = symbolTable.get(i).get(SIZECOLS);
				address += Integer.valueOf(size);
			}
		}
		
		return String.valueOf(address);
	}
}
