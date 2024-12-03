package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class SyntaxException extends Exception {
	private static final long serialVersionUID = 1L;
	private List<List<String>> tokens = new ArrayList<>();
	private int LINENUMCOLS = 3;
	
	// tokensリストを受け取るコンストラクタ
	public SyntaxException (List<List<String>> tokens) {
		this.tokens = tokens;
	}
	
	// エラーメッセージを設定するコンストラクタ
    public SyntaxException (String message) {
        super(message);
    }
	
	// エラーメッセージを例外のメッセージとして作成
    public void throwError(int index) throws SyntaxException {
    	String lineNum = tokens.get(index).get(LINENUMCOLS);
    	String message = "Syntax error: line " + lineNum; 
    	throw new SyntaxException(message);
    }
}

