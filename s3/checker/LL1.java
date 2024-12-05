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
     * 識別子名を登録するメソッド
     * 
     */
    
    
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
    	
    	// 型
    	Type type = new type();
    	
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
    	
    	
    	return new VariableName();
    }
    
    /**
     * 副プログラム宣言群
     * 
     */
    public SubprogramDeclarationGroup subprogramDeclarationGroup() throws SyntaxException {
    	
    }
}

