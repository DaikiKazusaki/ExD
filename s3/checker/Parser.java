package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    private int tokenIndex = 0;
    private int LEXICALCOLS = 0;
    private int TOKENCOLS = 1;
    private List<List<String>> tokens = new ArrayList<>();
    private SyntaxException e;

    public Parser(List<List<String>> tokens) {
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
     * トークンを取得するメソッド
     * 
     */
    public String getToken(int lineNum) {
    	return tokens.get(lineNum).get(TOKENCOLS);
    }
    
    /**
     * 字句を取得するメソッド
     * 
     */
    public String getLexicality(int lineNum) {
    	return tokens.get(lineNum).get(LEXICALCOLS);
    }
    
    /**
     * プログラム名の判定
     * 
     */
    public ProgramName programName() throws SyntaxException {
    	String token = getToken(tokenIndex);
    	
    	if (token.equals("SIDENTIFIER")) {
    		tokenIndex++;
    	} else {
    		e.throwError(tokenIndex);
    	}
    	
    	return new ProgramName(getLexicality(tokenIndex - 1));
    }
    
    /**
     * ブロックの判定
     * 
     */
    public Block block() throws SyntaxException {
    	VariableDeclaration variableDeclaration = variableDeclaration();    	
    	SubprogramDeclarationGroup subprogramDeclarationGroup = subprogramDeclarationGroup();
    	
    	return new Block(variableDeclaration, subprogramDeclarationGroup);
    }
    
    /**
     * 変数宣言
     * 
     */
    public VariableDeclaration variableDeclaration() throws SyntaxException {
    	String token = getToken(tokenIndex);
    	VariableDeclarationGroup variableDeclarationGroup = null;
    	
    	if (token.equals("SVAR")) {
    		tokenIndex++;
    		variableDeclarationGroup = variableDeclarationGroup();
    	}
    	
    	return new VariableDeclaration(variableDeclarationGroup);
    }
    
    /**
     * 変数宣言の並び
     * 
     */
    public VariableDeclarationGroup variableDeclarationGroup() throws SyntaxException {
    	VariableNameGroup variableNameGroup1 = variableNameGroup();
    	
    	// ":"の判定
    	checkToken("SCOLON");
    	
    	Type type1 = type();
    	
    	// ";"の判定
    	checkToken("SSEMICOLON");
    	
    	List<VariableNameGroup> variableNameGroup2 = new ArrayList<>();
    	List<Type> type2 = new ArrayList<>();
    	
    	while (getToken(tokenIndex).equals("SIDENTIFIER")) {
    		variableNameGroup2.add(variableNameGroup());
    		
    		// ":"の判定
        	checkToken("SCOLON");
        	
        	type2.add(type());
        	
        	// ";"の判定
        	checkToken("SSEMICOLON");
    	}
    	
    	return new VariableDeclarationGroup(variableNameGroup1, type1, variableNameGroup2, type2);
    }
    
    /**
     * 変数名の並び
     * 
     */
    public VariableNameGroup variableNameGroup() throws SyntaxException {
    	VariableName variableName1 = variableName();
    	List<VariableName> variableName2 = new ArrayList<>();
    	
    	while (getToken(tokenIndex).equals("SCOMMA")) {
    		tokenIndex++;
    		variableName2.add(variableName());
    	}
    	
    	return new VariableNameGroup(variableName1, variableName2);
    }
    
    /**
     * 変数名の判定
     * 
     */
    public VariableName variableName() throws SyntaxException {
    	String token = getToken(tokenIndex);
    	
    	if (token.equals("SIDENTIFIER")) {
    		tokenIndex++;
    	} else {
    		e.throwError(tokenIndex);
    	}
    	
    	return new VariableName(getLexicality(tokenIndex - 1));
    }
    
    /**
     * 型の判定
     * 
     */
    public Type type() throws SyntaxException {
    	GeneralType generalType = null;
    	ArrayType arrayType = null;
    	String token = getToken(tokenIndex);
    	
    	if (token.equals("SINTEGER") || token.equals("SCHAR") || token.equals("SBOOLEAN")) {
    		generalType = generalType();
    	} else if (token.equals("SARRAY")) {
    		tokenIndex++;
    		arrayType = arrayType();
    	} else {
    		e.throwError(tokenIndex);
    	}
    	
    	return new Type(generalType, arrayType);
    }
    
    /**
     * 標準型の判定
     * 
     */
    public GeneralType generalType() throws SyntaxException {
    	tokenIndex++;
    	return new GeneralType(getLexicality(tokenIndex - 1));
    }
    
    /**
     * 配列型の判定
     * 
     */
    public ArrayType arrayType() throws SyntaxException {
    	// "["の判定
    	checkToken("SLBRACKET");
    	
    	// 添え字の最小値
    	Integer minimumInteger = integer();
    	
    	// ".."の判定
    	checkToken("SRANGE");
    	
    	// 添え字の最大値
    	Integer maximumInteger = integer();
    	
    	// "]"の判定
    	checkToken("SRBRACKET");
    	
    	// "of"の判定
    	checkToken("SOF");
    	
    	// 標準型の判定
    	GeneralType generalType = generalType();
    	
    	return new ArrayType(minimumInteger, maximumInteger, generalType);
    }
    
    /**
     * 整数の判定
     * 
     */
    public Integer integer() throws SyntaxException {
    	Sign sign = null;
    	String token = getToken(tokenIndex);
    	
    	if (token.equals("SPLUS") || token.equals("SMINUS")) {
        	tokenIndex++;
    		sign = sign();
    	} 
    	
    	UnsignedInteger unsignedInteger = unsignedInteger();
    	
    	return new Integer(sign, unsignedInteger);
    }
    
    /**
     * 符号の判定
     * 
     */
    public Sign sign() throws SyntaxException {
    	return new Sign(getLexicality(tokenIndex - 1));
    }
    
    /**
     * 副プログラム宣言群の判定
     * 
     */
    public SubprogramDeclarationGroup subprogramDeclarationGroup() throws SyntaxException {
    	List<SubprogramDeclaration> subprogramDeclaration = new ArrayList<>();
    	
    	while (getToken(tokenIndex).equals("SPROCEDURE")) {    		
    		subprogramDeclaration.add(subprogramDeclaration());
    		// ";"の判定
    		checkToken("SEMICOLON");
    	}
    	
    	return new SubprogramDeclarationGroup(subprogramDeclaration);
    }
    
    /**
     * 副プログラム宣言
     * 
     */
    public SubprogramDeclaration subprogramDeclaration() throws SyntaxException {
    	SubprogramHead subprogramHead = subprogramHead();
    	VariableDeclaration variableDeclaration = variableDeclaration();
    	ComplexStatement complexStatement = complexStatement();
    	
    	return new SubprogramDeclaration(subprogramHead, variableDeclaration, complexStatement);
    }
    
    /**
     * 副プログラム頭部
     * 
     */
    public SubprogramHead subprogramHead() throws SyntaxException {
    	// "procedure"の判定
    	checkToken("SPROCEDURE");
    	
    	ProcedureName procedureName = procedureName();
    	
    	FormalParameter formalParameter = formalParameter();
    	
    	// ";"の判定
    	checkToken("SSEMICOLON");
    	
    	return new SubprogramHead(procedureName, formalParameter);
    }
    
    /**
     * 手続き名
     * 
     */
    public ProcedureName procedureName() throws SyntaxException {
    	String token = getToken(tokenIndex);
    	
    	if (token.equals("SIDENTIFIER")) {
    		tokenIndex++;
    	} else {
    		e.throwError(tokenIndex);
    	}
    	
    	return new ProcedureName(getLexicality(tokenIndex - 1));
    }
    
    /**
     * 仮パラメータ
     * 
     */
    public FormalParameter formalParameter() throws SyntaxException {
    	String token = getToken(tokenIndex);
    	FormalParameterGroup formalParameterGroup = null;
    	
    	if (token.equals("SLPAREN")) {
    		tokenIndex++;
    		formalParameterGroup = formalParameterGroup();
    		checkToken("SRPAREN");
    	}
    	
    	return new FormalParameter(formalParameterGroup);
    }
    
    /**
     * 仮パラメータの並び
     * 
     */
    public FormalParameterGroup formalParameterGroup() throws SyntaxException {
    	FormalParameterNameGroup formalParameterNameGroup1 = formalParameterNameGroup();
    	
    	// ":"の判定
    	checkToken("SCOLON");
    	
    	GeneralType generalType1 = generalType();
    	
    	List<FormalParameterNameGroup> formalParameterNameGroup2 = new ArrayList<>();
    	List<GeneralType> generalType2 = new ArrayList<>();
    	
    	while (getToken(tokenIndex).equals("SCOMMA")) {
    		tokenIndex++;
    		formalParameterNameGroup2.add(formalParameterNameGroup());
    		
    		// ":"の判定
    		checkToken("SCOLON");
    		
    		generalType2.add(generalType());
    	}
    	
    	
    	return new FormalParameterGroup(formalParameterNameGroup1, generalType1, formalParameterNameGroup2, generalType2);
    }
    
    /**
     * 仮パラメータ名の並び
     * 
     */
    public FormalParameterNameGroup formalParameterNameGroup() throws SyntaxException {
    	FormalParameterName formalParameterName1 = formalParameterName();
    	List<FormalParameterName> formalParameterName2 = new ArrayList<>();
    	
    	while (getToken(tokenIndex).equals("SCOMMA")) {
    		tokenIndex++;
    		formalParameterName2.add(formalParameterName());
    	}
    	
    	return new FormalParameterNameGroup(formalParameterName1, formalParameterName2);
    }
    
    /**
     * 仮パラメータ名
     * 
     */
    public FormalParameterName formalParameterName() throws SyntaxException {
    	String token = getToken(tokenIndex);
    	
    	if (token.equals("SIDENTIFIER")) {
    		tokenIndex++;
    	} else {
    		e.throwError(tokenIndex);
    	}
    	
    	return new FormalParameterName(getLexicality(tokenIndex - 1));
    }
    
    /**
     * 複合文の判定
     * 
     */
    public ComplexStatement complexStatement() throws SyntaxException {
    	// "begin"の判定
    	checkToken("SBEGIN");
    	
    	// 文の並び
    	StatementGroup statementGroup = statementGroup();
    	
    	// "end"の判定
    	checkToken("SEND");
    	
    	return new ComplexStatement(statementGroup);	
    }
    
    /**
     * 文の並びの判定
     * 
     */
    public StatementGroup statementGroup() throws SyntaxException {
    	// 文の判定
    	Statement statement1 = statement();
    	
    	// ";"の判定
    	checkToken("SSEMICOLON");
    	
    	List<Statement> statement2 = new ArrayList<>();
    	// 繰り返しの判定
    	while (getToken(tokenIndex).equals("SIDENTIFIER") || getToken(tokenIndex).equals("SREADLN") || getToken(tokenIndex).equals("SWRITELN") || getToken(tokenIndex).equals("SBEGIN")) {
    		statement2.add(statement());
    		
    		// ";"の判定
        	checkToken("SSEMICOLON");
    	}
    	
    	return new StatementGroup(statement1, statement2);
    }
    
    /**
     * 文の判定
     * 
     */
    public Statement statement() throws SyntaxException {
    	BasicStatement basicStatement = null;
    	IfThen ifthen = null;
    	WhileDo whileDo = null;
    	String token = getToken(tokenIndex);
    	
    	if (token.equals("SIDENTIFIER") || token.equals("SREADLN") || token.equals("SWRITELN") || token.equals("SBEGIN")) {
    		basicStatement = basicStatement();
    	}
    	
    	return new Statement(basicStatement, ifthen, whileDo);
    }
    
    /**
     * 基本文の判定
     * 
     */
    public BasicStatement basicStatement() throws SyntaxException {
    	String token = getToken(tokenIndex);
    	AssignStatement assignStatement = null;
    	ProcedureCallStatement procedureCallStatement = null;
    	InputOutputStatement inputOutputStatement = null;
    	ComplexStatement complexStatement = null;
    	
    	if (token.equals("SREADLN") || token.equals("SWRITELN")) {
    		inputOutputStatement = inputOutputStatement();
    	}
    	
    	return new BasicStatement(assignStatement, procedureCallStatement, inputOutputStatement, complexStatement);
    }
    
    /**
     * 変数の判定
     */
    public Variable variable() throws SyntaxException {
    	NaturalVariable naturalVariable = null;
    	VariableWithIndex variableWithIndex = null;
    	String token = getToken(tokenIndex);
    	
    	if (token.equals("SSTRING") || getToken(tokenIndex + 1).equals("SLBRACKET")) {
    		// variableWithArray = varuableWithArray();
    	} else if (token.equals("SSTRING")) {
    		naturalVariable = naturalVariable();
    	}
    	
    	return new Variable(naturalVariable, variableWithIndex);
    }
    
    /**
     * 純変数の判定
     * 
     */
    public NaturalVariable naturalVariable() throws SyntaxException {
    	VariableName variableName = variableName();
    	return new NaturalVariable(variableName);
    }
    
    /**
     * 式の並びの判定
     * 
     */
    public EquationGroup equationGroup() throws SyntaxException {
    	Equation equation1 = equation();
    	List<Equation> equation2 = new ArrayList<>();
    	
    	tokenIndex++;
    	if (getToken(tokenIndex).equals("SCOMMA")) {
    		tokenIndex++;
    		equation2.add(equation());
    		tokenIndex++;
    	}
    	
    	return new EquationGroup(equation1, equation2);
    }
    
    /**
     * 式の判定
     * 
     */
    public Equation equation() throws SyntaxException {
    	SimpleEquation simpleEquation1 = simpleEquation();
    	List<RelationalOperator> relationalOperator = new ArrayList<>();
    	List<SimpleEquation> simpleEquation2 = new ArrayList<>();
    	
    	String token = getToken(tokenIndex);
    	
    	if (token.equals("SPLUS") || token.equals("SMINUS") || token.equals("SOR")) {
    		tokenIndex++;
    		relationalOperator.add(relationalOperator());
    		simpleEquation2.add(simpleEquation());
    	}
    	
    	return new Equation(simpleEquation1, relationalOperator, simpleEquation2);
    }
    
    /**
     * 単純式の判定
     * 
     */
    public SimpleEquation simpleEquation() throws SyntaxException {
    	Sign sign = null;
    	Term term1 = term();
    	List<AdditionalOperator> additionalOperator = new ArrayList<>();
    	List<Term> term2 = new ArrayList<>();
    	String token = getToken(tokenIndex);
    	
    	while (token.equals("SPLUS") || token.equals("SMINUS")) {
    		tokenIndex++;
    		additionalOperator.add(additionalOperator());
    		tokenIndex++;
    		term2.add(term());
    		tokenIndex++;
    	} 
    	
    	return new SimpleEquation(sign, term1, additionalOperator, term2);
    }
    
    /**
     * 項の判定
     * 
     */
    public Term term() throws SyntaxException {
    	Factor factor1 = factor();
    	List<MultipleOperator> multipleOperator = new ArrayList<>();
    	List<Factor> factor2 = new ArrayList<>();
    	
    	String token = getToken(tokenIndex);
    	
    	while (token.equals("SSTAR") || token.equals("SDIVD") || token.equals("SMOD") || token.equals("SAND")) {
    		multipleOperator.add(multipleOperator());
    		factor2.add(factor());
    	}
    	
    	return new Term(factor1, multipleOperator, factor2);
    }
    
    /**
     * 因子の判定
     * 
     */
    public Factor factor() throws SyntaxException {
    	Variable variable = null;
    	Constant constant = null;
    	Equation equation = null;
    	Factor factor = null;    	
    	String token = getToken(tokenIndex);
    	
    	if (token.equals("SSTRING")) {
    		variable = variable();
    	} else if (token.equals("SCONSTANT")) {
    		// constant = constant();
    	} else if (token.equals("SLPAREN")) {
    		tokenIndex++;
    		equation = equation();
    		checkToken("SRPAREN");
    	} else if (token.equals("SNOT")) {
    		tokenIndex++;
    		factor = factor();
    	}
    	
    	return new Factor(variable, constant, equation, factor);
    }
    
    /**
     * 関係演算子の判定
     * 
     */
    public RelationalOperator relationalOperator() throws SyntaxException {
    	String token = getToken(tokenIndex);
    	
    	if (token.equals("SEQUALS") || token.equals("SNOTEQUAL") || token.equals("SLESS") || token.equals("SLESSEQUAL") || token.equals("SGREATEQUAL") || token.equals("SGREAT")) {
    		tokenIndex++;
    	} else {
    		e.throwError(tokenIndex);
    	}  	
    	
    	return new RelationalOperator(token);
    }
    
    /**
     * 加法演算子
     * 
     */
    public AdditionalOperator additionalOperator() throws SyntaxException {
    	String token = getToken(tokenIndex);
    	
    	if (token.equals("SPLUS") || token.equals("SMINUS") || token.equals("SOR")) {
    		tokenIndex++;
    	} else {
    		e.throwError(tokenIndex);
    	}
    	
    	return new AdditionalOperator(getLexicality(tokenIndex));
    }
    
    /**
     * 乗法演算子
     * 
     */
    public MultipleOperator multipleOperator() throws SyntaxException {
    	String token = getToken(tokenIndex);
    	
    	if (token.equals("SSTAR") || token.equals("SDIV") || token.equals("SMOD") || token.equals("SAND")) {
    		tokenIndex++;
    	} else {
    		e.throwError(tokenIndex);
    	}
    	
    	return new MultipleOperator(getLexicality(tokenIndex));
    }
    
    /**
     * 入出力文の判定
     * 
     */
    public InputOutputStatement inputOutputStatement() throws SyntaxException {
    	VariableGroup variableGroup = null;
    	EquationGroup equationGroup = null;
    	String token = getToken(tokenIndex);
    	
    	if (token.equals("SREADLN")) {
    		tokenIndex++;
    		// "("の判定
    		if (getToken(tokenIndex).equals("SLPAREN")) {
    			tokenIndex++;
    			// variableGroup = variableGroup();
    			// ")"の判定
    			checkToken("SRPAREN");
    		}
    	} else if (token.equals("SWRITELN")) {
    		tokenIndex++;
    		// "("の判定
    		if (getToken(tokenIndex).equals("SLPAREN")) {
    			tokenIndex++;
    			equationGroup = equationGroup();
    			// ")"の判定
    			checkToken("SRPAREN");
    		}
    	} 
    	
    	return new InputOutputStatement(variableGroup, equationGroup);
    }
    
    /**
     * 符号なし整数の判定
     * 
     */
    public UnsignedInteger unsignedInteger() throws SyntaxException {
    	String token = getToken(tokenIndex);
    	
    	if (token.equals("SCONSTANT")) {
    		tokenIndex++;
    	} else {
    		e.throwError(tokenIndex);
    	}
    	
    	return new UnsignedInteger(getLexicality(tokenIndex - 1));
    }
}