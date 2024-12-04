package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class LL1 {
    private int tokenIndex = 0;
    private int TOKENCOLS = 1;
    private List<List<String>> tokens = new ArrayList<>();
    private SyntaxException e;

    public LL1(List<List<String>> tokens) {
        this.tokens = tokens;
        e = new SyntaxException(tokens);
    }

    /**
     * プログラムの判定を行うメソッド
     * 
     * @throws SyntaxException
     */
    public void program() throws SyntaxException {
    	// "program"の判定
        checkToken("SPROGRAM");

        // プログラム名の判定
        checkToken("SIDENTIFIER");

        // ";"の判定
        checkToken("SSEMICOLON");
        
        // ブロックの判定
        block();
        
        // 複合文の判定
        complexStatement();
        
        // "."の判定
        checkToken("SDOT");
    }
    
    /**
     * トークンが正しいことを判定するメソッド
     * 
     * @throws SyntaxException
     */
    public void checkToken(String token) throws SyntaxException {
    	if (tokens.get(tokenIndex).get(TOKENCOLS).equals(token)) {
    		tokenIndex++;
    	} else {
    		e.throwError(tokenIndex);
    	}
    }

    /**
     * ブロックの判定を行うメソッド
     * 
     * @throws SyntaxException
     */
    public void block() throws SyntaxException {
    	// 変数宣言を判定するプログラム
    	if (isSVAR() == true) {
    		declareVariables();
    	}
    	
    	// 副プログラム宣言群の解析
    	subprogramDeclarations();
    }

    /**
     * 複合文の判定を行うメソッド
     * @return 
     * 
     * @throws SyntaxException
     */
    public void complexStatement() throws SyntaxException {
    	// "begin"の判定
    	checkToken("SBEGIN");
    	
    	// 文の並びの判定
    	sequenceOfSentences();
    	
    	// "end"の確認
    	checkToken("SEND");
    }
    
    /**
     * tokenが"SVAR"であるかの判定を行うメソッド
     * 
     * @return　true : token　== "SVAR"，変数宣言の並びの判定を行う
     * @return false: token != "SVAR"
     */
    public boolean isSVAR() {
    	if (tokens.get(tokenIndex).get(TOKENCOLS).equals("SVAR")) {
    		tokenIndex++;
        	return true;
        } else {
        	return false;
        }
    }

    /**
     * 変数宣言の並びを判定するメソッド
     * 複数の型の変数が宣言される場合，再帰を用いて判定する
     * 
     * @throws SyntaxException
     */
    public void declareVariables() throws SyntaxException {  
    	// 変数名の並びの判定
    	isVariableOrFormalParameters();
    	
    	// ":"の判定
    	checkToken("SCOLON");
    	
    	// 型の判定
    	isType();
    	
    	// ";"の判定
    	checkToken("SSEMICOLON");
    	
    	// 複数の変数が宣言される場合，再帰を行う
    	if (tokens.get(tokenIndex).get(TOKENCOLS).equals("SIDENTIFIER")) {
    		declareVariables();
    	}
    }
    
    /**
     * 変数名の並びを判定する
     * 
     * @throws SyntaxException
     */
    public void isVariableOrFormalParameters() throws SyntaxException {
    	String token = tokens.get(tokenIndex).get(TOKENCOLS);
    	
    	if (token.equals("SIDENTIFIER")) {
    		tokenIndex++;
    		isVariableOrFormalParameters();
    	} else if (token.equals("SCOMMA")) {
    		tokenIndex++;
    		token = tokens.get(tokenIndex).get(TOKENCOLS);
    		if (token.equals("SIDENTIFIER")) {
    			tokenIndex++;
    			isVariableOrFormalParameters();
    		} else {
    			e.throwError(tokenIndex);
    		}    		
    	} else if (token.equals("SCOLON")) {
    		return ;
    	} else {
    		e.throwError(tokenIndex);
    	}
    	
    }
    
    /**
     * 型の判定を行うメソッド
     *
     * @throws SyntaxException
     */
    public void isType() throws SyntaxException {
        String token = tokens.get(tokenIndex).get(TOKENCOLS);

        if (token.equals("SARRAY")) {
            tokenIndex++;
            isArray();
        } else if (token.equals("SINTEGER") || token.equals("SCHAR") || token.equals("SBOOLEAN")) {
            tokenIndex++; 
        } else {
            e.throwError(tokenIndex);
        }
    }

    /**
     * 配列型の判定を行うメソッド
     *
     * @throws SyntaxException
     */
    public void isArray() throws SyntaxException {
        // "["の判定
        checkToken("SLBRACKET");

        // 添字の最小値の判定
        if (isSignedConstant()) {
            tokenIndex++;
        } else {
            e.throwError(tokenIndex);
        }

        // ".."の判定
        checkToken("SRANGE");

        // 添字の最大値の判定
        if (isSignedConstant()) {
            tokenIndex++;
        } else {
            e.throwError(tokenIndex);
        }

        // "]"の判定
        checkToken("SRBRACKET");

        // "of"の判定
        checkToken("SOF");

        // 配列の要素の型を判定
        isType(); // 配列の中身の型（整数や文字など）を判定
    }

    /**
     * 符号付きの定数であるかを判定するメソッド
     * 
     * @return true: 符号付きまたは符号なしの定数である
     * @return false: 定数ではない
     */
    public boolean isSignedConstant() {
        // 符号の判定
        if (tokens.get(tokenIndex).get(TOKENCOLS).equals("SPLUS") || 
            tokens.get(tokenIndex).get(TOKENCOLS).equals("SMINUS")) {
            tokenIndex++;
        }

        // 数値（定数）の判定
        return tokens.get(tokenIndex).get(TOKENCOLS).equals("SCONSTANT");
    }

    
    /**
     * 副プログラム宣言群を判定するメソッド
     * 
     * @throws SyntaxException
     */
    public void subprogramDeclarations() throws SyntaxException {
    	// "SPROGRAM"の判定
    	if (isSPROCEDURE() == true) {
    		tokenIndex++;
    	} else {
    		return ;
    	}
    	
    	//　副プログラム頭部の判定
    	subprogramHeader();
    	
    	// 変数宣言の判定
    	if (isSVAR() == true) {
    		declareVariables();
    	}    	
    	
    	// 複合文の判定
    	complexStatement();
    	
    	// ";"の判定
    	checkToken("SSEMICOLON");
    	
    	// 副プログラム宣言が複数行われた場合
    	subprogramDeclarations();
    }
    
    /**
     * SPROCEDUREを判定するメソッド
     * 
     * @return true : "SPROCEDURE"である
     * @return false: "SPROCEDURE"でない
     */
    public boolean isSPROCEDURE() {
    	if (tokens.get(tokenIndex).get(TOKENCOLS).equals("SPROCEDURE")) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    /**
     * 副プログラム頭部の判定
     * 
     * @throws SyntaxException
     */
    public void subprogramHeader() throws SyntaxException {
    	// 手続き名の判定
    	checkToken("SIDENTIFIER");
    	
    	// 仮パラメータの判定
    	formalParameter();
    	
    	// ";"の判定
    	checkToken("SSEMICOLON");
    }
    
    /**
     * 仮パラメータの判定するメソッド
     * 
     * @throws SyntaxException
     */
    public void formalParameter() throws SyntaxException {
    	// "("を判定するメソッド
    	if (!tokens.get(tokenIndex).get(TOKENCOLS).equals("SLPAREN")) {
    		return ;
    	}
    	tokenIndex++;
    	
    	// 仮パラメータの並び
    	isFormalParameter();
    	
    	// ")"を判定するメソッド
    	checkToken("SRPAREN");
    }
    
    /**
     * 仮パラメータの並び
     * 
     * @throws SyntaxException
     */
    public void isFormalParameter() throws SyntaxException {  
    	// 変数名の並びの判定
    	isVariableOrFormalParameters();
    	
    	// ":"の判定
    	checkToken("SCOLON");
    	
    	// 型の判定
    	isGeneralType();
    	
    	// 複数の変数が宣言される場合，再帰を行う
    	if (tokens.get(tokenIndex).get(TOKENCOLS).equals("SIDENTIFIER")) {
    		// ";"の判定
        	checkToken("SSEMICOLON");
    		isFormalParameter();
    	}
    }
    
    /**
     * 型の判定を行うメソッド
     * 
     * @throws SyntaxException
     */
    public void isGeneralType() throws SyntaxException {
        String token = tokens.get(tokenIndex).get(TOKENCOLS);

        if (token.equals("SINTEGER") || token.equals("SCHAR") || token.equals("SBOOLEAN")) {
            tokenIndex++; 
        } else {
            e.throwError(tokenIndex);
        }
    }
    
    // ブロック文の判定はここまで(修正の必要な恐らくなし)
    // 以下は複合文の判定を行うメソッド
    // complexStatement() の定義は82行目～
    
    /**
     * 文の並びの判定を行うメソッド
     * 
     * @throws SyntaxException
     */
    public void sequenceOfSentences() throws SyntaxException {
    	// 文の判定
    	statement();
    	
    	// ";"の判定
    	checkToken("SSEMICOLON");
    	
    	// 再帰の判定
    	if (isStatement()) {
    		sequenceOfSentences();
    	} 
    }
    
    /**
     * 文の判定を行うメソッド
     * first = {SIDENTIFIER, SWRITELN, SREADLN, SBEGIN, SIF, SWHILE} で判定する
     * 
     * @return true : token == first
     * @return false: tokenがfirst以外
     */
    public boolean isStatement() {
    	String token = tokens.get(tokenIndex).get(TOKENCOLS);
    	
    	if (token.equals("SIDENTIFIER") || token.equals("SREADIN") || token.equals("SWRITELN") || token.equals("SBEGIN") || token.equals("SIF") || token.equals("SWHILE")) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    /**
     * 文の判定を行うメソッド
     * 
     * @throws SyntaxException
     */
    public void statement() throws SyntaxException {
    	String token = tokens.get(tokenIndex).get(TOKENCOLS);
    	
    	if (token.equals("SIDENTIFIER") || token.equals("SREADIN") || token.equals("SWRITELN") || token.equals("SBEGIN")) {
    		basicStatement();
    	} else if (token.equals("SIF")) {
        	tokenIndex++;
    		judgeIf();
    	} else if (token.equals("SWHILE")) {
        	tokenIndex++;
    		judgeWhile();
    	} else {
    		e.throwError(tokenIndex);
    	}
    }
    
    /**
     * 基本文の判定を行うメソッド
     * 代入文または手続き呼び出しの判定に誤りあり
     *
     * @throws SyntaxException
     */
    public void basicStatement() throws SyntaxException {
        String token = tokens.get(tokenIndex).get(TOKENCOLS);
        
        if (token.equals("SIDENTIFIER")) { // 代入文，手続き呼び出しの判定
        	isAssignmentOrProcedure();
        } else if (token.equals("SREADLN")) { // 入力文の判定
            tokenIndex++;
            readln();
        } else if (token.equals("SWRITELN")) { // 出力文の判定
            tokenIndex++;
            writeln();
        } else if (token.equals("SBEGIN")) { // 複合文の判定
            complexStatement();
        } else {
            e.throwError(tokenIndex);
        }
    }

    /**
     * 代入文と手続き呼び出し文の判定を行うメソッド
     * 
     * @throws SyntaxException 
     */
    public void isAssignmentOrProcedure() throws SyntaxException {
    	// 変数名，手続き呼び出し名の判定
    	isVariable();
    	
    	// 代入文，手続き呼び出し文の判定
    	String token = tokens.get(tokenIndex).get(TOKENCOLS);
    	if (token.equals("SASSIGN")) {
    		tokenIndex++;
    		equations();
    	} else {
    		procedureCallStatement();
    	}
    }
    
    /**
     * 手続き呼び出し文の呼び出し
     * 
     * @throws SyntaxException
     */
    public void procedureCallStatement() throws SyntaxException {
    	// "("の判定
    	if (tokens.get(tokenIndex).get(TOKENCOLS).equals("SLPAREN")) {
    		tokenIndex++;
    	} else {
    		return ;
    	}
    	
    	// 式の並びの判定
    	equations();
    	while (tokens.get(tokenIndex).get(TOKENCOLS).equals("SCOMMA")) {
    		tokenIndex++;
    		equations();
    	}
    	
    	// ")"の判定
    	checkToken("SRPAREN");
    }
    
    /**
     * 変数の判定
     * 
     * @throws Exception
     */
    public void isVariable() throws SyntaxException {
    	// 変数名の判定
    	checkToken("SIDENTIFIER");
    	
    	// "["の判定
    	if (!tokens.get(tokenIndex).get(TOKENCOLS).equals("SLBRACKET")) {
            return ;
        } 
    	tokenIndex++;
    	
    	//　添え字( = 式)の判定
    	equations();
    	
    	// "]"の判定
        checkToken("SRBRACKET");
    }
    
    /**
     * 式の判定
     * 
     * @throws SyntaxException
     */
    public void equations() throws SyntaxException {
    	// 単純式の判定
    	simpleEquations();
    	
    	// 関係演算子の判定
    	String token = tokens.get(tokenIndex).get(TOKENCOLS);
    	if (token.equals("SEQUAL") || token.equals("SNOTEQUAL") || token.equals("SLESS") || token.equals("SLESSEQUAL") || token.equals("SGREAT") || token.equals("SGREATEQUAL")) {
    		tokenIndex++;
    	} else {
    		return ;
    	}
    	    	
    	// 単純式の判定
    	simpleEquations();
    }
    
    /**
     * 単純式の判定
     * 
     * @throws SyntaxException
     */
    public void simpleEquations() throws SyntaxException {
        // 符号の判定
        // tokenが"+" or "-"の場合，tokenIndex++
        if (tokens.get(tokenIndex).get(TOKENCOLS).equals("SPLUS") || tokens.get(tokenIndex).get(TOKENCOLS).equals("SMINUS")) {
            tokenIndex++;
        }
        
        // 項の判定
        term();
        
        // {加法演算子 項} の部分を判定するループ
        while (tokens.get(tokenIndex).get(TOKENCOLS).equals("SPLUS") || 
               tokens.get(tokenIndex).get(TOKENCOLS).equals("SMINUS") || 
               tokens.get(tokenIndex).get(TOKENCOLS).equals("SOR")) {
            tokenIndex++;
            
            // 次の項の判定
            term();
        }
    }
    
    /**
     * 項を判定するメソッド
     * 
     * @throws SyntaxException
     */
    public void term() throws SyntaxException {
    	// 因子の判定
    	factor();
    	
    	// 乗法演算子を判定し，その後因子を判定
    	while (multiplicativeOperators()) {
    		tokenIndex++;
    		
    		// 因子の判定
    		factor();
    	}
    }
    
    /**
     * 因子を判定するメソッド
     * 
     * @throws SyntaxException
     */
    public void factor() throws SyntaxException {
        String token = tokens.get(tokenIndex).get(TOKENCOLS);
    	
    	if (token.equals("SIDENTIFIER")) { // 変数の判定
    		isVariable();
    	} else if (isConstant(token)) { // 定数の判定
    		tokenIndex++;
    	} else if (token.equals("SLPAREN")) { // "(" 式 ")" の判定
    		tokenIndex++;
    		
    		//　式の判定
    		equations();
    		
    		// ")"の判定
    		checkToken("SRPAREN");
    	} else if (token.equals("SNOT")) { // "not" 因子 の判定
    		tokenIndex++;
    		term();
    	} else {
    		e.throwError(tokenIndex);
    	}
    }
    
    /**
     * 乗法演算子を判定するメソッド
     * 
     * @return true : SSTAR, SDIVD, SMOD, SAND
     * @return false: その他
     */
    public boolean multiplicativeOperators() {
    	String token = tokens.get(tokenIndex).get(TOKENCOLS);
    	
    	if (token.equals("SSTAR") || token.equals("SDIVD") || token.equals("SMOD") || token.equals("SAND")) {
    		return true;
    	}
    	return false;
    }
    
    /**
     * 定数を判定するメソッド
     * 
     * @return true : SCONSTANT, SSTRING, SFALSE, STRUE
     * @return false: 上記以外
     */
    public boolean isConstant(String token) {
    	if (token.equals("SCONSTANT") || token.equals("SSTRING") || token.equals("SFALSE") || token.equals("STRUE")) { 
    		return true;
    	} else {
    		return false;
    	}
    }
    
    /**
     * if文を判定するメソッド
     * 
     * @throws SyntaxException
     */
    public void judgeIf() throws SyntaxException {
    	// "if"の判定は終了済みなので，式の判定からスタート    	
    	// 式の判定
    	equations();
    	
    	// "then"の判定
    	checkToken("STHEN");
    	
    	// 複合文の判定
    	complexStatement();
    	
    	// "else"の判定
    	if (!tokens.get(tokenIndex).get(TOKENCOLS).equals("SELSE")) {
    		return ;
    	}
    	tokenIndex++;
    	
    	// 複合文の判定
    	complexStatement();
    }
    
    /**
     * while文の判定
     * 
     * @throws SyntaxException
     */
    public void judgeWhile() throws SyntaxException {
    	// "while"の判定は終了済みなので，式の判定からスタート    	
    	// 式の判定
    	equations();
    	
    	// "do"の判定
    	checkToken("SDO");
    	
    	// 複合文の判定
    	complexStatement();
    }
    
    /**
     * readlnの判定
     * 
     * @throws SyntaxException
     */
    public void readln() throws SyntaxException {
    	// "readln"の判定は実行済み
    	// "("の判定
    	checkToken("SLPAREN");
    	
    	// 変数の並びの判定
    	isVariable();
    	while (tokens.get(tokenIndex).get(TOKENCOLS).equals("SCOMMA")) {
    		tokenIndex++;
    		isVariable();
    	}
    	
    	// ")"の判定
    	checkToken("SRPAREN");
    }
    
    /**
     * writelnの判定
     * 
     * @throws SyntaxException
     */
    public void writeln() throws SyntaxException {
    	// "writeln"の判定は実行済み
    	// "("の判定から開始
    	checkToken("SLPAREN");
    	
    	// 式の並びの判定
    	equations();
    	while (tokens.get(tokenIndex).get(TOKENCOLS).equals("SCOMMA")) {
    		tokenIndex++;
    		equations();
    	}
    	
    	// ")"の判定
    	checkToken("SRPAREN");
    }
}

