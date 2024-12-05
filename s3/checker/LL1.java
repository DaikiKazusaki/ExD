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
     * 字句を取得するメソッド
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
    	// 変数宣言
		VariableDeclaration variableDeclaration = variableDeclaration();
    	    	
    	// 副プログラム宣言群の判定
    	SubprogramDeclarationGroup subprogramDeclarationGroup = subprogramDeclarationGroup();
    	
    	return new Block(variableDeclaration, subprogramDeclarationGroup);
    }
    
    /**
     * 変数宣言
     */
    public VariableDeclaration variableDeclaration() throws SyntaxException {
    	// "SVAR"の判定
    	if (!isToken("SVAR")) {
    		return null;
    	}    	
    	
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
    	
    	// "SCOLON"の判定
    	checkToken("SCOLON");
    	
    	return new VariableNameGroup(variableName);
    }
    
    /**
     * 変数名
     * 
     */
    public VariableName variableName() throws SyntaxException {
    	// 識別子の判定 
    	checkToken("SIDENTIFIER");
    	
    	return new VariableName(getLexical(tokenIndex - 1));
    }
    
    /**
     * 型
     * 
     */
    public Type type() throws SyntaxException {
    	String token = getToken(tokenIndex);
    	GeneralType generalType = null;
    	ArrayType arrayType = null;
    	
    	if (token.equals("SINTEGER") || token.equals("SCHAR") || token.equals("SBOOLEAN")) {
    		// 標準型の判定
    		generalType = generalType();
    	} else if (token.equals("SARRAY")) {
    		// 配列型の判定
        	arrayType = arrayType();
    	} else {
    		e.throwError(tokenIndex);
    	}
    	
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
    	
    	// 手続き名
    	ProcedureName procedureName = procedureName();
    	
    	// 仮パラメータ
    	FormalParameter formalParameter = formalParameter();
    	
    	// "SSEMICOLON"の判定
    	checkToken("SSEMICOLON");
    	
    	return new SubprogramHead(procedureName, formalParameter);
    }
    
    /**
     * 手続き名
     * 
     */
    public ProcedureName procedureName() throws SyntaxException {
    	// 識別子の判定
    	checkToken("SIDENTIFIER");
    	
    	return new ProcedureName(getLexical(tokenIndex - 1));
    }
    
    /**
     * 仮パラメータ
     * 
     */
    public FormalParameter formalParameter() throws SyntaxException {
    	// "SLPAREN"の判定
    	checkToken("SLPAREN");
    	
    	// 仮パラメータの並び
    	FormalParameter formalParameterGroup = formalParameterGroup();
    	
    	// "SRPAREN"
    	checkToken("SRPAREN");
    	
    	return new FormalParameter(formalParameterGroup);
    }
    
    /**
     * 仮パラメータの並び
     * 
     */
    public FormalParameterGroup formalParameterGroup() throws SyntaxException {
    	// 仮パラメータ名の並び
    	FormalParameterNameGroup formalParameterNameGroup = formalParameterNameGroup();
    	
    	// "SCOLON"
    	checkToken("SCOLON");
    	
    	// 標準型
    	GeneralType generalType = generalType();
    	
    	return new FormalParameterGroup(formalParameterNameGroup, generalType);
    }
    
    /**
     * 仮パラメータ名の並び
     * 
     */
    public FormalParameterNameGroup formalParameterNameGroup() throws SyntaxException {
    	// 仮パラメータ名
    	FormalParameterName formalParameterName = new formalParameterName();
    	
    	// "SCOLON"の判定
    	checkToken("SCOLON");
    	
    	return new FormalParameterNameGroup(formalParameterName);
    }
    
    /**
     * 仮パラメータ名
     * 
     */
    public FormalParameterName formalParameterName() throws SyntaxException {
    	// 識別子の判定
    	checkToken("SIDENTIFIER");
    	
    	return new FormalParameterName(getLexical(tokenIndex - 1));
    }
    
    /**
     * 複合文
     */
    public ComplexStatement complexStatement() throws SyntaxException {
    	// "SBEGIN"の判定
    	checkToken("SBEGIN");
    	
    	// 文の並びの判定
    	StatementGroup statementGroup = statementGroup();
    	
    	// "SEND"の判定
    	checkToken("SEND");
    	
    	return new ComplexStatement(statementGroup);
    }
    
    /**
     * 文の並び
     * 
     */
    public StatementGroup statementGroup() throws SyntaxException {
    	// 文の判定
    	Statement statement = statement();
    	
    	// "SSEMICOLON"の判定
    	checkToken("SSEMICOLON");
    	
    	return new StatementGroup(statement);
    }
    
    /**
     * 文
     * 
     */
    public Statement statement() throws SyntaxException {
    	String token = getToken(tokenIndex);
    	BasicStatement basicStatement = null;
    	IfThen ifThen = null;
    	WhileDo whileDo = null;
    	
    	if (token.equals("SIF")) {
    		// if-then
        	ifThen = ifThen();
    	} else if (token.equals("SWHILE")) {
    		// while-do
    		whileDo = whileDo();
    	}
    	// 基本文
    	basicStatement = basicStatement();    	
    	
    	
    	
    	
    	
    	return new Statement(basicStatement, ifThenElse, ifThen, whileDo);    	
    }
    
    /**
     * if-then-else
     * 
     */
    public IfThen ifThen() throws SyntaxException {
    	// "SIF"の判定
    	checkToken("SIF");
    	
    	// 式の判定
    	Equation equation = equation();
    	
    	// "STHEN"の判定
    	checkToken("STHEN");
    	
    	// 複合文の判定
    	ComplexStatement complexStatement = complexStatement();
    	
    	// "SIF"の判定
    	String token = getToken(tokenIndex);
    	Else Else = null;
    	if (token.equals("SIF")) {
    		Else = Else();
    	}
    	
    	return new IfThen(equation, complexStatement, Else);
    }
    
    /**
     * else
     * 
     */
    public Else Else() throws SyntaxException {
    	// "SIF"の判定は終わっているので，tokenIndexをインクリメントのみ行う
    	tokenIndex++;
    	
    	ComplexStatement complexStatement = complexStatement();
    	
    	return new Else(complexStatement);
    }
    
    /**
     * while-do
     * 
     */
    public WhileDo whileDo() throws SyntaxException {
    	// "SWHILE"の判定
    	checkToken("SWHILE");
    	
    	// 式の判定
    	Equation equation = equation();
    	
    	// "SDOの判定
    	checkToken("SDO");
    	
    	// 複合文の判定
    	ComplexStatement complexStatement = complexStatement();
    	
    	return new WhileDo(equation, complexStatement);
    }
    
    /**
     * 基本文の判定
     * 
     */
    public BasicStatement basicStatement() throws SyntaxException {
    	// 代入文
    	AssignStatement assignStatement = assignStatement();
    	
    	// 手続き呼び出し文
    	ProcedureCallStatement procedureCallStatement = procedureCallStatement();
    	
    	// 入出力文
    	InputOutputStatement inputOutputStatement = inputOutputStatement();
    	
    	// 複合文
    	ComplexStatement complexStatement = complexStatement();
    	
    	return new BasicStatement(assignStatement, procedureCallStatement, inputOutputStatement, complexStatement);
    }
    
    /**
     * 代入文
     * 
     */
    public AssignStatement assignStatement() throws SyntaxException {
    	// 左辺
    	LeftSide leftSide = leftSide();
    	
    	// "SASSIGN"の判定
    	checkToken("SASSIGN");
    	
    	// 式
    	Equation equation = equation();
    	
    	return new AssignStatement(leftSide, equation);
    }
    
    /**
     * 左辺
     * 
     */
    public LeftSide leftSide() throws SyntaxException {
    	// 変数の判定
    	Variable variable = variable();
    	
    	return new LeftSide(variable);
    }
    
    /**
     * 変数
     * 
     */
    public Variable variable() throws SyntaxException {
    	//　純変数
    	NaturalVariable naturalVariable = naturalVariable();
    	
    	// 添え字付き変数
    	VariableWithIndex variableWithIndex = variableWithIndex();
    	
    	return new Variable(naturalVariable, variableWithIndex);
    }
    
    /**
     * 純変数
     * 
     */
    public NaturalVariable naturalVariable() throws SyntaxException {
    	// 変数名
    	VariableName variableName = variableName();
    	
    	return new NaturalVariable(variableName);
    }
    
    /**
     * 添え字付き変数
     * 
     */
    public VariableWithIndex variableWithIndex() throws SyntaxException {
    	// 変数名の判定
    	VariableName variableName = variableName();
    	
    	// "SLBRACKET"の判定
    	checkToken("SLBRACKET");
    	
    	// 添え字の判定
    	Index index = index();
    	
    	// "SRBRACKET"の判定
    	checkToken("SRBRACKET");
    	
    	return new VariableWithIndex(variableName, index);
    }
    
    /**
     * 添え字
     * 
     */
    public Index index() throws SyntaxException {
    	// 式の判定
    	Equation equation = equation();
    	
    	return new Index(equation);
    }
    
    /**
     * 手続き呼び出し文
     * 
     */
    public ProcedureCallStatement procedureCallStatement() throws SyntaxException {
    	// 手続き名
    	ProcedureName procedureName = procedureName();
    	
    	// "SLPAREN"の判定
    	checkToken("SLPAREN");
    	
    	//　式の並び
    	EquationGroup equationGroup = equationGroup();
    	
    	// "SRPAREN"の判定
    	checkToken("SRPAREN");
    	
    	return new ProcedureCallStatement(procedureName, equationGroup);
    }
    
    /**
     * 式の並び
     * 
     */
    public EquationGroup equationGroup() throws SyntaxException {
    	// 式の判定
    	Equation equation1 = equation();
    	
    	// "SCOLON"の判定
    	checkToken("SCOLON");
    	
    	// 式の判定
    	Equation equation2 = equation();
    	
    	return new EquationGroup(equation1, equation2);
    }
    
    /**
     * 式の判定
     * 
     */
    public Equation equation() throws SyntaxException {
    	// 単純式の判定
    	SimpleEquation simpleEquation1 = simpleEquation();
    	
    	// 関係演算子の判定
    	String token = getToken(tokenIndex);
    	RelationalOperator relationalOperator = null;
    	SimpleEquation simpleEquation2 = null;
    	
    	if (token.equals("SEQUAL") || token.equals("SNOTEQUAL") || token.equals("SLESS") || token.equals("SLESSEQUAL") || token.equals("SGREATEQUAL") || token.equals("SGREAT")) {
    		relationalOperator = relationalOperator();
    		simpleEquation2 = simpleEquation();
    	}
    	
    	return new Equation(simpleEquation1, relationalOperator, simpleEquation2);
    }
    
    /**
     * 単純式の判定
     * 
     */
    public SimpleEquation simpleEquation() throws SyntaxException {
    	// 符号の判定
    	String token = getToken(tokenIndex);
    	Sign sign = null;
    	if (token.equals("SPLUS") || token.equals("SMINUS")) {
    		sign = sign();
    	}
    	
    	// 項の判定
    	Term term1 = term();
    	
    	// 加法演算子の判定
    	AdditionalOperator additonalOperator = additonalOperator();
    	
    	// 項の判定
    	Term term2 = term();
    }
    
    /**
     * 項の判定
     * 
     */
    public Term term() throws SyntaxException {
    	// 因子の判定
    	Factor factor1 = factor();
    	
    	// 乗法演算子の判定
    	MultipleOperator multipleOperator = multipleOperator();
    	
    	// 因子の判定
    	Factor factor2 = factor();
    	
    	return new Term(factor1, multipleOperator, factor2);
    }
    
    /**
     * 因子の判定
     * 
     */
    public Factor factor() throws SyntaxException {
    	// 変数の判定
    	// 式の判定
    	Equation equation = equation();
    	
    	// "not"因子
    	
    }
    
    /**
     * 関係演算子
     * 
     */
    public RelationalOperator relationalOperator() throws SyntaxException {
    	String token = getToken(tokenIndex);
    	
    	if (token.equals("SEQUAL") || token.equals("SNOTEQUAL") || token.equals("SLESS") || token.equals("SLESSEQUAL") || token.equals("SGREATEQUAL") || token.equals("SGREAT")) {
    		return new RelationalOperator(token);
    	} else {
    		return null;
    	}
    }
    
    /**
     * 乗法演算子
     * 
     */
    public MultipleOperator multipleOperator() throws SyntaxException {
    	String token = getToken(tokenIndex);
    	
    	if (token.equals("SSTAR") || token.equals("SDIV") || token.equals("SMOD") || token.equals("SAND")) {
    		return new MultipleOperator(getLexical(tokenIndex - 1));
    	} else {
    		return null;
    	}    	
    }
    
    /**
     * 入出力文
     * 
     */
    public InputOutputStatement inputOutputStatement() throws SyntaxException {
    	String token = getToken(tokenIndex);
    	VariableGroup variableGroup = null;
    	EquationGroup equationGroup = null;
    	
    	if (token.equals("SREADLN")) {
    		// 入力文
    		variableGroup = variableGroup();
    	} else if (token.equals("SWRITELN")) {
    		// 出力文
    		equationGroup = equationGroup();
    	} else {
    		e.throwError(tokenIndex);
    	}
    	
    	return new InputOutputStatement(variableGroup, equationGroup);
    }
    
    /**
     * 変数の並び
     * 
     */
    public VariableGroup variableGroup() throws SyntaxException {
    	// 変数
    	Variable variable1 = variable();
    	
    	// "SCOLON"の判定
    	checkToken("SCOLON");
    	
    	// 変数の判定
    	Variable variable2 = variable();
    	
    	return new VariableGroup(variable1, variable2);
    }
    
    /**
     * 定数
     */
    public Constant constant() throws SyntaxException {
    	String token = getToken(tokenIndex);
    	
    	if (token.equals("SSTRING") || token.equals("SFALSE") || token.equals("STRUE")) {
    		return new Constant(getLexical(tokenIndex - 1), null);
    	} else if (token.equals("SCONSTANT")) {
    		UnsignedInteger unsignedInteger = unsignedInteger();
    		return new Constant(null, unsignedInteger);
    	} else {
    		e.throwError(tokenIndex);
    		return null;
    	}
    }
    
    /**
     * 符号なし整数
     * 
     */
    public UnsignedInteger unsignedInteger() throws SyntaxException {
    	// "SCONSTANT"の判定
    	checkToken("SCONSTANT");
    	
    	return new UnsignedInteger(getLexical(tokenIndex - 1));
    }
}

