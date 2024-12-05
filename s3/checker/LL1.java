package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class LL1 {
    private int tokenIndex = 0;
    private int LEXERICALCOLS = 0;
    private int TOKENCOLS = 1;
    private List<List<String>> tokens = new ArrayList<>();
    private SyntaxException e;

    public LL1(List<List<String>> tokens) {
        this.tokens = tokens;
        e = new SyntaxException(tokens);
    }

    /**
     * プログラム
     * 
     */
    public Program program() throws SyntaxException {
    	// "program"の判定
        checkToken("SPROGRAM");

        // プログラム名の判定
        ProgramName programName = programName();

        // ";"の判定
        checkToken("SSEMICOLON");
        
        // ブロックの判定
        Block block = block();
        
        // 複合文の判定
        ComplexStatement complexStatement = complexStatement();
        
        // "."の判定
        checkToken("SDOT");
        
        return new Program(programName, block, complexStatement); 
    }
    
    /**
     * トークンが正しいことを判定するメソッド
     * 
     */
    public void checkToken(String token) throws SyntaxException {
    	if (tokens.get(tokenIndex).get(TOKENCOLS).equals(token)) {
    		tokenIndex++;
    	} else {
    		e.throwError(tokenIndex);
    	}
    }
    
    /**
     * 選択[]部の判定を行うメソッド
     * 
     */
    public boolean isToken(String token) {
    	if (tokens.get(tokenIndex).get(TOKENCOLS).equals(token)) {
    		tokenIndex++;
    		return true;
    	} else {
    		return false;
    	}
    }
    
    /**
     * 識別子名を取得するメソッド
     * 
     */
    public String getLexical(int lineNum) {
    	return tokens.get(lineNum).get(LEXERICALCOLS);
    }
    
    /**
     * トークンを取得するメソッド
     * 
     */
    public String getToken(int lineNum) {
    	return tokens.get(lineNum).get(TOKENCOLS);
    }
    
    /**
     * プログラム名
     * 
     */
    public ProgramName programName() throws SyntaxException {
    	if (tokens.get(tokenIndex).get(TOKENCOLS).equals("SIDENTIFIER")) {
    		tokenIndex++;
    	} else {
    		e.throwError(tokenIndex);
    	}
    	
		return new ProgramName(tokens.get(tokenIndex).get(LEXERICALCOLS));
    }

    /**
     * ブロック
     * 
     */
    public Block block() throws SyntaxException {
    	// 変数宣言の判定
    	if (isToken("SVAR")) {
    		VariableDeclaration variableDeclaration = variableDeclaration();
    	}
    	
    	// 副プログラム宣言群の判定
    	SubprogramDeclarationGroup subprogramDeclarationGroup = subprogramDeclaratioinGroup();
    	
    	return new Block(variableDeclaration, subprogramDeclarationGroup);
    }
    
    /**
     * 変数宣言
     */
    public VariableDeclaration variableDeclaration() throws SyntaxException {
    	// 変数宣言の並び
    	VariableDeclarationGroup variableDeclarationGroup = variableDeclarationGroup();
    	
    	return new VariableDeclaration(variableDeclarationGroup);
    }
    
    /**
     * 変数宣言の並び
     * 
     */
    public VariableDeclarationGroup variableDeclarationGroup() throws SyntaxException {
    	// 変数名の並び
    	VariableNameGroup variableNameGroup = variableNameGroup();
    	
    	// "SCOLON"の判定
    	checkToken("SCOLON")
    	
    	// 型
    	Type type = type();
    	
    	// "SSEMICOLON"の判定
    	checkToken("SSEMICOLON");
    	
    	return new VariableDeclarationGroup(variableNameGroup, type);
    }
    
    /**
     * 変数名の並び
     * 
     */
    public VariableNameGroup variableNameGroup() throws SyntaxException {
    	// 変数名の判定
    	VariableName variableName = variableName();
    	
    	return new VariableNameGroup(variableName);
    }
    
    /**
     * 変数名
     * 
     */
    public VariableName variableName() throws SyntaxException {
    	// 識別子の判定
    	if (isToken("SIDENTIFIER")) {
    		return new VariableName(getLexical(tokenIndex));
    	} else {
    		e.throwError(tokenIndex);
    		return null;
    	}    	
    }
    
    /**
     * 型
     * 
     */
    public Type type() throws SyntaxException {
    	// 標準型の判定
    	GeneralType generalType = generalType();
    	
    	// 配列型の判定
    	ArrayType arrayType = arrayType();
    	
    	return new Type(generalType, arrayType);
    }
    
    /**
     * 標準型
     * 
     */
    public GeneralType generalType() throws SyntaxException {
    	if (isToken("SINTEGER") || isToken("SCHAR") || isToken("SBOOLEAN")) {
    		return new GeneralType(getToken(tokenIndex));
    	}
    	
    	return null;
    }
    
    /**
     * 配列型
     * 
     */
    public ArrayType arrayType() throws SyntaxException {
    	// "SARRAY"の判定
    	if (!isToken("SARRAY")) {
    		e.throwError(tokenIndex);
    		return null;
    	}
    	
    	// 添え字の最小値
    	Integer minimumIndex = integer();
    	
    	// "SDOT"の判定
    	if (!isToken("SDOT")) {
    		e.throwError(tokenIndex);
    		return null;
    	}
    	
    	// 添え字の最大値
    	Integer maximumIndex = integer();
    	
    	// "SOF"
    	if (!isToken("SOF")) {
    		e.throwError(tokenIndex);
    		return null;
    	}
    	
    	// 標準型
    	GeneralType generalType = generalType();
    	
    	return new ArrayType(minimumIndex, maximumIndex, generalType);
    }
    
    /**
     * 整数
     * 
     */
    public Integer integer() throws SyntaxException {
    	// 符号
    	Sign sign = sign(); 
    	
    	// 符号なし整数
    	UnsignedInteger unsignedInteger = unsignedInteger();
    	
    	return new Integer(sign, unsignedInteger);
    }
    
    /**
     * 符号
     * 
     */
    public Sign sign() throws SyntaxException {
    	// "SPLUS", "SMINUS"の判定
    	if (isToken("SPLUS") || isToken("SMINUS")) {
    		return new Sign(getLexical(tokenIndex));
    	} else {
    		return null;
    	}
    }
    
    /**
     * 副プログラム宣言群
     * 
     */
    public SubprogramDeclarationGroup subprogramDeclarationGroup() throws SyntaxException {
    	// 副プログラム宣言
    	SubprogramDeclaration subprogramDeclaration = subprogramDeclaration();
    	
    	// "SSEMICOLON"の判定
    	checkToken("SSEMICOLON");
    	
    	return SubprogramDeclaration(subprogramDeclaration);
    }
    
    /**
     * 副プログラム宣言
     * 
     */
    public SubprogramDeclaration subprogramDeclaration() throws SyntaxException {
    	// 副プログラム頭部
    	SubprogramHead subprogramHead = subprogramHead();
    	
    	// 変数宣言
    	VariableDeclaration variableDeclaration = variableDeclaration(); 
    	
    	// 複合文
    	ComplexStatement complexStatement = complexStatement();
    	
    	return new SubprogramDeclaration(subprogramHead, variableDeclaration, complexStatement);
    }
    
    /**
     * 副プログラム頭部
     * 
     */
    public SubprogramHead subprogramHead() throws SyntaxException {
    	// "SPROCEDURE"の判定
    	checkToken("SPROCEDURE");
    }
    
    /**
     * 符号なし整数
     * 
     */
    public UnsignedInteger unsignedInteger() throws SyntaxException {
    	
    }
}

