package enshud.s3.checker;

import java.util.HashMap;

public class SymbolTable {
	HashMap<String, String> symbolTable = new HashMap<>();
	
	// 記号表の作成
	public void addSymbolTable(String name, String type) {
		symbolTable.put(name, type);
	}
	
	// 記号表を取得するメソッド
	public HashMap<String, String> getSymbolTable(){
		return symbolTable;
	}
}
