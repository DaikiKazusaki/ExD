package enshud.s4.compiler;

import java.util.ArrayList;
import java.util.List;

public class SymbolTable {
	/**
	 * 記号表の情報
	 * [変数名，標準型，配列の判定，サイズ]
	 * 
	 */
	public List<List<String>> symbolTable = new ArrayList<>();
	private int NAMECOLS = 0;
	private int SCOPECOLS = 3;
	
	/**
	 * 記号表に登録するメソッド
	 * 
	 * @param name
	 * @param type
	 * @param isArray
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
	 * 記号表にすでに登録されているか
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
}
