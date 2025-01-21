package enshud.s4.compiler;

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
	 * 変数の領域を取得するメソッド
	 * 
	 * @return
	 */
	public String getSizeOfVar() {
		int sum = 0;
		for (Symbol symbol: symbolTable) {
			int size = symbol.getSize();
			sum += size;
		}
		
		return String.valueOf(sum);
	}
	
	/**
	 *　変数のアドレスを取得するメソッド
	 * 
	 * @return
	 */
	public String getAddressOfSymbol(String name, String scope) {
		int address = 0;
		boolean isFound = false;
		
		// スコープ内で探索
		for (Symbol symbol: symbolTable) {
			String variableName = symbol.getVariableName();
			String functionName = symbol.getScope();
			if (name.equals(variableName) && scope.equals(functionName)) {
				isFound = true;
				break;
			} else {
				int size = symbol.getSize();
				address += size;
			}
		}
		
		// スコープ内に存在しない場合，グローバルで探索
		if (isFound == false) {
			address = 0;
			for (Symbol symbol: symbolTable) {
				String variableName = symbol.getVariableName();
				String functionName = symbol.getScope();
				if (name.equals(variableName) && "global".equals(functionName)) {
					break;
				} else {
					int size = symbol.getSize();
					address += size;
				}
			}
		}
		
		return String.valueOf(address);
	}
	
	/**
	 * 仮引数のサイズを取得するメソッド
	 * 
	 * @param functionName
	 * @return
	 */
	public String getSizeOfFormalParameter(String scope) {
		int count = 0;
		
		for (Symbol symbol: symbolTable) {
			String functionName = symbol.getScope();
			boolean isFormalParameter = symbol.isFormalParameter();
			if (functionName.equals(scope) && isFormalParameter == true) {
				int size = symbol.getSize();
				count += size;
			}
		}
		
		return String.valueOf(count);
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
	
	/**
	 * 変数を使用済みに変更するメソッド
	 * 
	 * @param variableName
	 * @param scope
	 */
	public void usedVariable(String variableName, String functionName) {
	    boolean isFound = false;

	    // スコープ内の探索
	    for (Symbol symbol: symbolTable) {
	    	String name = symbol.getVariableName();
	    	String scope = symbol.getScope();
	    	if (variableName.equals(name) && functionName.equals(scope)) {
	    		symbol.setISUsed();
	    		isFound = true;
	            break;
	    	}
	    }

	    // スコープ内で見つからない場合，グローバルスコープを探索
	    if (!isFound) {
	    	for (Symbol symbol: symbolTable) {
		    	String name = symbol.getVariableName();
		    	String scope = symbol.getScope();
		    	if (variableName.equals(name) && "global".equals(scope)) {
		    		symbol.setISUsed();
		            break;
		    	}
		    }
	    }
	}

	/**
	 * 未使用変数を表示し，symbolTableから削除するメソッド
	 *
	 * @param inputFileName
	 */
	public void printWarning(final String inputFileName) {
	    boolean hasWarnings = false;
	    List<Symbol> unusedSymbols = new ArrayList<>();
	    
	    // 未使用変数の検出と警告の表示
	    for (Symbol symbol : symbolTable) {
	        boolean isUsed = symbol.isUsed();
	        if (!isUsed) {
	            if (!hasWarnings) {
	                System.out.println("Warning: in " + inputFileName);
	                hasWarnings = true;
	            }
	            String name = symbol.getVariableName();
	            String lineNum = symbol.getLineNum();
	            System.out.println('\t' + name + " is declared in line " + lineNum + ", but never used.");
	            
	            // 未使用の変数をリストに追加
	            unusedSymbols.add(symbol);
	        }
	    }

	    // 未使用変数を記号表から削除
	    symbolTable.removeAll(unusedSymbols);
	}
}