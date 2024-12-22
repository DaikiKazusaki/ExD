package enshud.s4.compiler;

import java.util.ArrayList;
import java.util.List;

public class FunctionTable {
	List<String> functionTable = new ArrayList<>();
	
	public void addFunctionTable(String functionName) {
		functionTable.add(functionName);
	}
	
	public boolean isAbleToAddFunctionTable(String name) {
		if (functionTable.contains(name)) {
			return false;
		} else {
			return true;
		}
	}
}
