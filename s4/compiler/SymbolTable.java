package enshud.s4.compiler;

import java.util.ArrayList;
import java.util.List;

public class SymbolTable {
	/**
	 * 記号表の情報
	 * [変数名，標準型，配列の判定，サイズ]
	 * 
	 */
	public List<List<String>> SymbolTable = new ArrayList<>();
	
	/**
	 * 記号表に登録するメソッド
	 * 
	 * @param name
	 * @param type
	 * @param isArray
	 */
	public void addSymbol(String name, String type, String isArray) {
		List<String> newSymbolInformation = new ArrayList<>();
		newSymbolInformation.add(name);
		newSymbolInformation.add(type);
		newSymbolInformation.add(isArray);
		
		SymbolTable.add(newSymbolInformation);
	}
}
