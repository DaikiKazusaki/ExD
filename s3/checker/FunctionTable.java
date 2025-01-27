package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class FunctionTable {
	List<String> functionTable = new ArrayList<>();
	
	/**
	 * 関数表に副プログラム名を追加するメソッド
	 * 
	 * @param functionName
	 */
	public void addFunctionTable(String functionName) {
		functionTable.add(functionName);
	}
	
	/**
	 * 記号表を取得するメソッド
	 * 
	 * @return
	 */
	public List<String> getFunctionTable() {
		return functionTable;
	}
	
	/**
	 * 関数表に副プログラム名を追加できるかを判定するメソッド
	 * 
	 * @param name
	 * @return
	 */
	public boolean isAbleToAddFunctionTable(String name) {
		if (functionTable.contains(name)) {
			return false;
		} else {
			return true;
		}
	}
}
