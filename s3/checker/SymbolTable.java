package enshud.s3.checker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SymbolTable {
	HashMap<String, List<String>> symbolTable = new HashMap<>();
	
	// 記号表の作成
	public void addSymbolTable(String name, String type, int scope) {		
		List<String> typeAndScope = new ArrayList<>(); // 新しいリストを作成
		typeAndScope.add(type);
		typeAndScope.add(String.valueOf(scope));
		symbolTable.put(name, typeAndScope);
	}
	
	// 記号表を取得するメソッド
	public HashMap<String, List<String>> getSymbolTable(){
		return symbolTable;
	}
}

