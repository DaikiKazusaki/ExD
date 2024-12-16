package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class FunctionTable {
	private List<String> functionTable = new ArrayList<>();
	
	/**
	 * 関数表に手続き名を登録する処理
	 * 
	 */
	public void addProcedureName(String procedureCallName) {
		functionTable.add(procedureCallName);
	}
	
	/**
	 * 関数表を取得するメソッド
	 * 
	 */
	public List<String> getFunctionTable(){
		return functionTable;
	}
}
